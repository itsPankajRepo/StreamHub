package com.stream.hub.userMgmt.dto.request;

import com.stream.hub.userMgmt.constants.MessageConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateUserProfileRequest {
    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private String firstName;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private String lastName;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private List<String> songPreferences;

    @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
    @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
    private List<String> videoPreferences;

}
