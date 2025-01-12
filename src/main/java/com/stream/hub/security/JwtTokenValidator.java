package com.stream.hub.security;

import com.stream.hub.userMgmt.constants.GenericConstant;
import com.stream.hub.userMgmt.exception.StreamHubException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.stream.hub.userMgmt.constants.GenericConstant.*;

public class JwtTokenValidator extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader(GenericConstant.AUTHORIZATION) != null) {
            String token = request.getHeader(GenericConstant.AUTHORIZATION);
            try {
                Claims claims = JwtTokenUtil.extractClaims(token);
                var username = claims.get(USERNAME, String.class);
                var authorities = claims.get(AUTHORITIES, String.class);
                var password = claims.get(PASSWORD, String.class);
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException e) {
                throw new StreamHubException("Invalid token",400);
            }
        }

        filterChain.doFilter(request, response);
    }
}
