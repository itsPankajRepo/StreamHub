package com.stream.hub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class RequestResponseLogging extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("************ Printing the request ************");
        var startTime = System.currentTimeMillis();
        try {
            String urlType = request.getMethod();
            String uri = request.getRequestURI();
            if (urlType.equals("GET")) {
                log.info("{} {}", urlType, uri);
                if (!request.getParameterMap().isEmpty())
                    log.info("{} {} {}", urlType, uri, request.getParameterMap());

            } else {
                log.info("{} {} {}", urlType, uri, request.getContentType());
                String line;
                var requestBuffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        requestBuffer.append(line);
                    }
                    var requestBody = requestBuffer.toString();
                    log.info("Request body :: {}", requestBody);
                }

            }

            filterChain.doFilter(request, response);
        }finally {
            log.info("************ Printing the response ************");
            response.flushBuffer();
        }
        log.info("Time taken for processing api : {}{}", (System.currentTimeMillis() - startTime), "ms");

    }
}
