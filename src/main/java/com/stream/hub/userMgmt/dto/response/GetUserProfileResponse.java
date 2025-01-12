package com.stream.hub.userMgmt.dto.response;

import com.stream.hub.userMgmt.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserProfileResponse {

    private long id;

    private String username;

    private int age;

    private String firstName;

    private String lastName;

    private String fullName;

    private List<String> songPreferences;

    private List<String> videoPreferences;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private UserRole role;
}
