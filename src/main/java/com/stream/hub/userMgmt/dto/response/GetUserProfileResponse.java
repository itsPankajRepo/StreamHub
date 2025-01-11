package com.stream.hub.userMgmt.dto.response;

import com.stream.hub.userMgmt.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
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

    private Timestamp createdOn;

    private Timestamp updatedOn;

    private UserRole role;
}
