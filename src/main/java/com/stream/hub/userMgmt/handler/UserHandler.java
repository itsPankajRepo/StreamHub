package com.stream.hub.userMgmt.handler;

import com.stream.hub.security.JwtTokenUtil;
import com.stream.hub.userMgmt.Utils.GenericUtils;
import com.stream.hub.userMgmt.constants.GenericConstant;
import com.stream.hub.userMgmt.dto.genric.ApiResponse;
import com.stream.hub.userMgmt.dto.request.LoginRequestRequest;
import com.stream.hub.userMgmt.dto.request.ResetPasswordRequest;
import com.stream.hub.userMgmt.dto.request.UserSignUpRequest;
import com.stream.hub.userMgmt.dto.response.GetUserProfileResponse;
import com.stream.hub.userMgmt.exception.StreamHubException;
import com.stream.hub.userMgmt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final GenericUtils genericUtils;


    public ApiResponse<Object> registerUser(@Valid UserSignUpRequest userSignUpRequest) {
        if (userService.checkUserExistWithUsername(userSignUpRequest.getUsername()))
            throw new StreamHubException("User exist with the username - " + userSignUpRequest.getUsername() + ", Please use different username");
        var encodedPassword = passwordEncoder.encode(userSignUpRequest.getPassword());
        userSignUpRequest.setPassword(encodedPassword);
        var savedUser = userService.saveNewUser(userSignUpRequest);
        if (Objects.nonNull(savedUser))
            return new ApiResponse<>("User Created Successfully", 200);

        return new ApiResponse<>("Not able to register, Please try again later", 500);
    }


    public ApiResponse<Object> login(@Valid LoginRequestRequest loginRequestRequest) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestRequest.username(),
                loginRequestRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (Objects.nonNull(authenticationResponse) && authenticationResponse.isAuthenticated()) {
            var token = JwtTokenUtil.generateJWTToken(authenticationResponse);
            var result = new HashMap<String, String>();
            result.put(GenericConstant.ACCESS_TOKEN, token);
            return new ApiResponse<>("Login Successful", 200, GenericConstant.SUCCESS, result);
        }
        return new ApiResponse<>("Not able to login, Please try again later", 500);
    }


    public ApiResponse<Object> resetPassword(@Valid ResetPasswordRequest request) {
        if (userService.checkUserExistWithUsername(request.username()))
            throw new StreamHubException("User exist with the username - " + request.username() + ", Please use different username");

        var encodedPassword = passwordEncoder.encode(request.newPassword());

        if (userService.updatePassword(request.username(), encodedPassword))
            return new ApiResponse<>("Password Updated Successfully", 200);

        return new ApiResponse<>("Not able to update password, Please try again later", 400);

    }


    public ApiResponse<Object> getUserProfile() {
        var token = genericUtils.getHeaderValue(GenericConstant.AUTHORIZATION);
        var username = JwtTokenUtil.getUsernameFromToken(token);
        var user = userService.getUser(username);
        GetUserProfileResponse userProfileResponse = new GetUserProfileResponse();
        BeanUtils.copyProperties(user, userProfileResponse);
        return new ApiResponse<>("User profile fetched successfully", 200, GenericConstant.SUCCESS, userProfileResponse);
    }
}
