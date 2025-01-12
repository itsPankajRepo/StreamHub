package com.stream.hub.userMgmt.dto.request;

import com.stream.hub.userMgmt.enums.UserRole;

public record UpdateUserRoleRequest(Long id, UserRole role) {
}
