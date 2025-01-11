package com.stream.hub.userMgmt.entity;

import com.stream.hub.userMgmt.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private int age;

    private String firstName;

    private String lastName;

    private String fullName;

    private List<String> songPreferences;

    private List<String> videoPreferences;

    @CreationTimestamp
    private Timestamp createdOn;

    @UpdateTimestamp
    private Timestamp updatedOn;

    private UserRole role;


    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", updatedOn=" + updatedOn +
                ", createdOn=" + createdOn +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", songPreferences=" + songPreferences +
                ", videoPreferences=" + videoPreferences +
                '}';
    }

}
