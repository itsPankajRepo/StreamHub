package com.stream.hub.userMgmt.dto.request;

import com.stream.hub.userMgmt.constants.MessageConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteUserAccountRequest(
        @NotNull(message = MessageConstant.MANDATORY_FIELDS_NOT_FOUND)
        @NotEmpty(message = MessageConstant.MANDATORY_FIELD_IS_EMPTY)
        List<Long> userIds) {
}
