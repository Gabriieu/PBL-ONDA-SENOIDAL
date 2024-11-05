package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@NoArgsConstructor
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String JWT_SECRET = "F8d45G2!sdP#F8d4K13e@zR$8f4D5gyl2S77";


    public static Key generateKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }


    public static JwtToken generateToken(String username, String role) {
        Date now = new Date();

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token);
    }

    private static Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(refactoredToken(token))
                    .getBody();
        } catch (JwtException exception) {
            log.error("Invalid token - " + exception.getMessage());
        }

        return null;
    }

    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(refactoredToken(token));
            return true;
        } catch (JwtException exception) {
            log.error("Invalid token - " + exception.getMessage());
        }

        return false;
    }

    private static String refactoredToken(String token) {
        if (token != null && token.startsWith(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }

        return token;
    }
}
