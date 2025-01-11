package com.stream.hub.userMgmt.service;

import com.stream.hub.userMgmt.dto.genric.ApiResponse;
import com.stream.hub.userMgmt.dto.request.UserSignUpRequest;
import com.stream.hub.userMgmt.repo.UserRepository;
import com.stream.hub.userMgmt.entity.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public boolean checkUserExistWithUsername(String userName) {
        var userOptional = userRepository.findByUsername(userName);
        return userOptional.isPresent();
    }

    public User saveNewUser(UserSignUpRequest userSignUpRequest) {
        var newUser = new User();
        BeanUtils.copyProperties(userSignUpRequest, newUser);
        var fullName = userSignUpRequest.getLastName() == null ? userSignUpRequest.getFirstName() : userSignUpRequest.getFirstName().concat(" ").concat(userSignUpRequest.getLastName());
        newUser.setFirstName(fullName);
        return userRepository.save(newUser);
    }

    public boolean updatePassword( String username, String encodedPassword) {
        var updatedRows = userRepository.updatePassword(username, encodedPassword);
        return  updatedRows > 0;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
