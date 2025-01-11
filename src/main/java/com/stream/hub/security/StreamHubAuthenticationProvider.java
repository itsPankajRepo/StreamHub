package com.stream.hub.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StreamHubAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final StreamHubUserDetailsService streamHubUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Inside StreamHubAuthenticationProvider :: authenticate having authentication {}", authentication);

        var user = streamHubUserDetailsService.loadUserByUsername(authentication.getName());
        if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        throw new BadCredentialsException("Bad credentials for username + " + authentication.getName());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
