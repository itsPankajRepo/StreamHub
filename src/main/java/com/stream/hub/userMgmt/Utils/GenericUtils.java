package com.stream.hub.userMgmt.Utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenericUtils {

    private final HttpServletRequest httpServletRequest;


    public String getHeaderValue(String headerName) {
        return httpServletRequest.getHeader(headerName);
    }
}
