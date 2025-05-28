package com.swacademy.newsletter.security;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration; // milliseconds

    // 토큰 발급
    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰 검증 및 클레임 추출
    public Claims extractClaims(String token) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서도 클레임 추출
            return e.getClaims();
        }
    }

//    // 토큰에서 유저 ID 추출
//    public Long getUserIdFromToken(String token) {
//        if (token == null || token.isBlank()) {
//            throw new IllegalArgumentException("토큰이 비어 있거나 null 입니다.");
//        }
//
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//
//        try {
//            return extractClaims(token).get("userId", Long.class);
//        } catch (Exception e) {
//            throw new GeneralException(ErrorStatus.JWT_MALFORMED);
//        }
//
////        return Long.parseLong(Jwts.parser()
////                .setSigningKey(secretKey)
////                .parseClaimsJws(token)
////                .getBody()
////                .getSubject());
//    }

    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.JWT_MALFORMED);
        }
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
