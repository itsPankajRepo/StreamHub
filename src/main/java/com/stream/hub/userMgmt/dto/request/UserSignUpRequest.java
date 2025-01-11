package com.stream.hub.userMgmt.dto.request;

import com.stream.hub.userMgmt.constants.MessageConstant;
import com.stream.hub.userMgmt.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

    @Email
    private String username;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private String password;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    private Integer age;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private String firstName;

    private String LastName;

    private List<String> songPreferences;

    private List<String> videoPreferences;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private UserRole role;


}
