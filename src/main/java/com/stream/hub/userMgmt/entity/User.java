package com.stream.hub.userMgmt.entity;

import com.stream.hub.userMgmt.attributeConvertor.ListToStringConvertor;
import com.stream.hub.userMgmt.enums.UserRole;
import com.stream.hub.userMgmt.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "users")
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

    @Convert(converter = ListToStringConvertor.class)
    private List<String> songPreferences;

    @Convert(converter = ListToStringConvertor.class)
    private List<String> videoPreferences;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


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
