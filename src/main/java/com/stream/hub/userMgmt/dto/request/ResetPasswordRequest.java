package com.stream.hub.userMgmt.dto.request;

import com.stream.hub.userMgmt.constants.MessageConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequest(
        @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
        @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
        String username,

        @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
        @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
        String newPassword) {
}
