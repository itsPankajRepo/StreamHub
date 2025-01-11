package com.stream.hub.userMgmt.controller;

import com.stream.hub.userMgmt.constants.GenericConstant;
import com.stream.hub.userMgmt.constants.RequestURIConstants;
import com.stream.hub.userMgmt.dto.genric.ApiResponse;
import com.stream.hub.userMgmt.dto.request.LoginRequestRequest;
import com.stream.hub.userMgmt.dto.request.ResetPasswordRequest;
import com.stream.hub.userMgmt.dto.request.UserSignUpRequest;
import com.stream.hub.userMgmt.handler.UserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RequestURIConstants.STREAM_HUB_USER)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserHandler userHandler;


    @PostMapping(value = RequestURIConstants.SIGN_UP)
    public ApiResponse<Object> signUp(@Valid @RequestBody UserSignUpRequest request) {
        log.info("Inside UserController :: signUp having request {}", request);
        return userHandler.registerUser(request);
    }


    @PostMapping(value = RequestURIConstants.LOGIN)
    public ApiResponse<Object> logIn(@Valid @RequestBody LoginRequestRequest request){
        log.info("Inside UserController :: logIn having request {}", request);
        return userHandler.login(request);
    }


    @PostMapping(value = RequestURIConstants.LOGOUT)
    public void logOut(){}


    @PostMapping(value = RequestURIConstants.FORGET_PASSWORD)
    public void forgetPassword(){}


    @PostMapping(value = RequestURIConstants.RESET_PASSWORD)
    public ApiResponse<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        return userHandler.resetPassword(request);
    }


    @GetMapping(value = RequestURIConstants.GET_USER_PROFILE)
    public ApiResponse<Object> getUserProfile(@RequestHeader(GenericConstant.AUTHORIZATION) String authorizationHeader){
        return userHandler.getUserProfile();
    }
}




