package com.stream.hub.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtil {

    public static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getCurrentUserCredentials() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
