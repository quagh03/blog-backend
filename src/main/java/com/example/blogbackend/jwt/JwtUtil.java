package com.example.blogbackend.jwt;

import com.example.blogbackend.entity.Role;
import com.example.blogbackend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Key SECRET_KEY = loadSecretKey();

    private static Key loadSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static String generateToken(User user) {
        String jws = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getRole).toArray())
                .setIssuedAt(new Date())
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
        System.out.println("Generated Token: " + jws);
        return jws;
    }

    public static Jws<Claims> parseToken(String token) {
        try {
            token = token.replace("Bearer ", "");
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT token", e);
        }
    }
}
