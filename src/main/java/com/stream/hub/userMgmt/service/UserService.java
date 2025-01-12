package com.stream.hub.userMgmt.service;

import com.stream.hub.userMgmt.dto.request.UpdateUserProfileRequest;
import com.stream.hub.userMgmt.dto.request.UserSignUpRequest;
import com.stream.hub.userMgmt.entity.User;
import com.stream.hub.userMgmt.enums.UserRole;
import com.stream.hub.userMgmt.enums.UserStatus;
import com.stream.hub.userMgmt.exception.StreamHubException;
import com.stream.hub.userMgmt.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
        newUser.setFullName(fullName);
        newUser.setStatus(UserStatus.ACTIVE);
        return userRepository.save(newUser);
    }

    public void updatePassword(String username, String encodedPassword) {
        var user = getUser(username);
        user.setPassword(encodedPassword);
        userRepository.save(user);

    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new StreamHubException("User not found with username : " + username, 400));
    }

    public Object updateUser(String currentUserName, UpdateUserProfileRequest request) {
        var user = getUser(currentUserName);
        BeanUtils.copyProperties(request, user);
        var fullName = request.getLastName() == null ? request.getFirstName() : request.getFirstName().concat(" ").concat(request.getLastName());
        user.setFullName(fullName);
        return userRepository.save(user);
    }

    public boolean deleteAllUser(List<Long> ids) {
        return userRepository.deleteUserById(ids) > 0;

    }

    public boolean restoreUser(List<Long> ids) {
        return userRepository.restoreUser(ids) > 0;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new StreamHubException("No User found with id : " + id, 400));
    }

    public User updateRole(User user, UserRole role) {
        user.setRole(role);
        return  userRepository.save(user);
    }

    public boolean checkUserIdsExist(List<Long> userIds) {
        return userRepository.findAllById(userIds).size() != userIds.size();
    }
}
