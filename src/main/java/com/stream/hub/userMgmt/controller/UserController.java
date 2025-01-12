package com.stream.hub.userMgmt.controller;

import com.stream.hub.userMgmt.annotation.ApiAccessType;
import com.stream.hub.userMgmt.constants.RequestURIConstants;
import com.stream.hub.userMgmt.dto.genric.ApiResponse;
import com.stream.hub.userMgmt.dto.request.*;
import com.stream.hub.userMgmt.enums.ApiAccessValue;
import com.stream.hub.userMgmt.handler.UserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RequestURIConstants.STREAM_HUB_USER)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserHandler userHandler;


    @ApiAccessType(ApiAccessValue.PUBLIC)
    @PostMapping(value = RequestURIConstants.SIGN_UP)
    public ApiResponse<Object> signUp(@Valid @RequestBody UserSignUpRequest request) {
        log.info("Inside UserController :: signUp having request {}", request);
        return userHandler.registerUser(request);
    }

    @ApiAccessType(ApiAccessValue.PUBLIC)
    @PostMapping(value = RequestURIConstants.LOGIN)
    public ApiResponse<Object> logIn(@Valid @RequestBody LoginRequestRequest request){
        log.info("Inside UserController :: logIn having request {}", request);
        return userHandler.login(request);
    }


    @PostMapping(value = RequestURIConstants.LOGOUT)
    public void logOut(){}


    @PostMapping(value = RequestURIConstants.FORGET_PASSWORD)
    public void forgetPassword(){}


    @ApiAccessType(ApiAccessValue.AUTHENTICATED)
    @PostMapping(value = RequestURIConstants.RESET_PASSWORD)
    public ApiResponse<Object> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        return userHandler.resetPassword(request);
    }


    @ApiAccessType(ApiAccessValue.AUTHENTICATED)
    @GetMapping(value = RequestURIConstants.GET_USER_PROFILE)
    public ApiResponse<Object> getUserProfile(){
        return userHandler.getUserProfile();
    }


    @ApiAccessType(ApiAccessValue.AUTHENTICATED)
    @PostMapping(value = RequestURIConstants.UPDATE_USER_PROFILE)
    public ApiResponse<Object> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest request){
        return userHandler.updateUserProfile(request);
    }


    @ApiAccessType(ApiAccessValue.AUTHENTICATED)
    @PostMapping(value = RequestURIConstants.DELETE_USER_ACCOUNT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Object> deleteUserAccount(@Valid @RequestBody DeleteUserAccountRequest request){
        return userHandler.deleteUserAccount(request);
    }


    @ApiAccessType(ApiAccessValue.AUTHENTICATED)
    @PostMapping(value = RequestURIConstants.ENABLE_USER_ACCOUNT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Object> enableUserAccount(@Valid @RequestBody EnableUserAccountRequest request){
        return userHandler.enableUserAccount(request);
    }


    @ApiAccessType(ApiAccessValue.AUTHENTICATED)
    @PostMapping(value = RequestURIConstants.UPDATE_ROLE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Object> updateUserRole(@Valid @RequestBody UpdateUserRoleRequest request){
        return userHandler.updateUserRole(request);
    }



}




