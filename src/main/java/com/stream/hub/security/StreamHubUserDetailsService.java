package com.stream.hub.security;

import com.stream.hub.userMgmt.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StreamHubUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside StreamHubUserDetailsService :: loadUserByUsername having username {}", username);
        var user =  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not not with userName : "+username));
        log.info("User found :: {}", user);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        return User.builder().username(user.getUsername()).password(user.getPassword()).authorities(grantedAuthority).build();
    }
}
