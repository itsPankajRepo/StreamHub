package com.stream.hub.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@Slf4j
public class Config {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        ApiRequestMatcher publicMatcher = new ApiRequestMatcher(ApiAccessValue.PUBLIC);
//        ApiRequestMatcher authenticatedMatcher = new ApiRequestMatcher(ApiAccessValue.AUTHENTICATED);

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new RequestResponseLogging(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/stream-hub/api/user/signup","/stream-hub/api/user/login").permitAll()
                        .requestMatchers("/stream-hub/api/user/reset-password","/stream-hub/api/user/me","/stream-hub/api/user/update-profile",
                        "/stream-hub/api/user/delete-account","/stream-hub/api/user/enable-account","/stream-hub/api/user/update-role").authenticated());
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();

    }

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
