package com.stream.hub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static com.stream.hub.userMgmt.constants.GenericConstant.*;

public class JwtTokenUtil {


    private static final String key = "qwertyuioplkjhgfdsazxcvbnm";

    public static String generateJWTToken(Authentication authentication) {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().issuer(STEAM_HUB)
                .subject(JWT_TOKEN)
                .claim(USERNAME, authentication.getName())
                .claim(AUTHORITIES, authentication.getAuthorities())
                .expiration(new java.util.Date(System.currentTimeMillis() + 600000))
                .signWith(secretKey).compact();

    }


    public static Claims extractClaims(String jwtToken) {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(jwtToken).getPayload();
    }



    public static String getUsernameFromToken(String token) {
        return extractClaims(token).get(USERNAME, String.class);
    }


    public static String getAuthorityFromToken(String token) {
        return extractClaims(token).get(AUTHORITIES, String.class);
    }





}
