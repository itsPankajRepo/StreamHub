package com.stream.hub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static com.stream.hub.userMgmt.constants.GenericConstant.*;

public class JwtTokenUtil {


    private static final String key = "o1p/HnM647evoV74ye+txvoHrr0PRM0c2Ad5vLlLa6Y=";

    public static String generateJWTToken(Authentication authentication) {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        var authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder().issuer(STEAM_HUB)
                .subject(JWT_TOKEN)
                .claim(USERNAME, authentication.getName())
                .claim(AUTHORITIES, authorities)
                .claim(PASSWORD, authentication.getCredentials())
                .expiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(secretKey).compact();

    }


    public static Claims extractClaims(String jwtToken) throws BadCredentialsException {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(jwtToken).getPayload();
    }

}
