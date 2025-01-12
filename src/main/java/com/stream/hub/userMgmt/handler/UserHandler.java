package com.stream.hub.userMgmt.handler;

import com.stream.hub.security.CurrentUserUtil;
import com.stream.hub.security.JwtTokenUtil;
import com.stream.hub.userMgmt.Utils.GenericUtils;
import com.stream.hub.userMgmt.constants.GenericConstant;
import com.stream.hub.userMgmt.dto.genric.ApiResponse;
import com.stream.hub.userMgmt.dto.request.*;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
            throw new StreamHubException("User exist with the username - " + userSignUpRequest.getUsername() + ", Please use different username", 400);
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
        var userCurrentPassword = CurrentUserUtil.getCurrentUserCredentials();
        if (!passwordEncoder.matches(request.currentPassword(), userCurrentPassword))
            throw new StreamHubException("Current password is incorrect", 400);

        userService.updatePassword(CurrentUserUtil.getCurrentUserName(), passwordEncoder.encode(request.newPassword()));
        return new ApiResponse<>("Password Updated Successfully", 200);
    }


    public ApiResponse<Object> getUserProfile() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.getUser(username);
        GetUserProfileResponse userProfileResponse = new GetUserProfileResponse();
        BeanUtils.copyProperties(user, userProfileResponse);
        return new ApiResponse<>("User profile fetched successfully", 200, GenericConstant.SUCCESS, userProfileResponse);
    }


    public ApiResponse<Object> updateUserProfile(UpdateUserProfileRequest request) {
        var updatedUser = userService.updateUser(CurrentUserUtil.getCurrentUserName(), request);
        GetUserProfileResponse userProfileResponse = new GetUserProfileResponse();
        BeanUtils.copyProperties(updatedUser, userProfileResponse);
        return new ApiResponse<>("User Updated successfully", 200, GenericConstant.SUCCESS, userProfileResponse);
    }


    public ApiResponse<Object> deleteUserAccount(DeleteUserAccountRequest request) {
        if (userService.checkUserIdsExist(request.userIds()))
            throw new StreamHubException("Invalid user ids provided", 400);
        if (userService.deleteAllUser(request.userIds()))
            return new ApiResponse<>("Users deleted successfully", 200);
        return new ApiResponse<>("Not able to delete user", 500);

    }

    public ApiResponse<Object> enableUserAccount(EnableUserAccountRequest request) {
        if (userService.checkUserIdsExist(request.userIds()))
            throw new StreamHubException("Invalid user ids provided", 400);
        if (userService.restoreUser(request.userIds()))
            return new ApiResponse<>("Users restore successfully", 200);
        return new ApiResponse<>("Not able to restore user", 500);

    }

    public ApiResponse<Object> updateUserRole(UpdateUserRoleRequest request) {
        var user = userService.getUser(request.id());
        var updatedUser = userService.updateRole(user, request.role());
        GetUserProfileResponse userProfileResponse = new GetUserProfileResponse();
        BeanUtils.copyProperties(updatedUser, userProfileResponse);
        return new ApiResponse<>("Users updated successfully", 200, GenericConstant.SUCCESS, userProfileResponse);
    }
}
