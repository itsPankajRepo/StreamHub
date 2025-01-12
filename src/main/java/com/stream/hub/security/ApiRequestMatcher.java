package com.stream.hub.security;

import com.stream.hub.userMgmt.annotation.ApiAccessType;
import com.stream.hub.userMgmt.enums.ApiAccessValue;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Objects;


@RequiredArgsConstructor
public class ApiRequestMatcher implements RequestMatcher {

    private final ApiAccessValue apiAccessValue;


    @Override
    public boolean matches(HttpServletRequest request) {
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (handler instanceof HandlerMethod method) {
            ApiAccessType apiAnnotation = method.getMethodAnnotation(ApiAccessType.class);
            return apiAnnotation != null && Objects.equals(apiAccessValue, apiAnnotation.value());
        }
        return false;
    }
}
