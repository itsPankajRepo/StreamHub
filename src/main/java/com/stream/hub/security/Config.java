package com.stream.hub.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class Config {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager  authenticationManager(StreamHubUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        StreamHubAuthenticationProvider authenticationProvider = new StreamHubAuthenticationProvider(passwordEncoder,userDetailsService);
        return new ProviderManager(authenticationProvider);
    }

}
