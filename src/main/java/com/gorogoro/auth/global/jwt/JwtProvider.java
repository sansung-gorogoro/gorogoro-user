package com.gorogoro.auth.global.jwt;

import com.gorogoro.auth.global.exception.BaseException;
import com.gorogoro.auth.global.exception.code.GlobalErrorCode;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    private final PrivateKey privateKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtProvider(
            @Value("classpath:private_key.pem") Resource privateKeyResource,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.privateKey = loadPrivateKey(privateKeyResource);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    /**
     * private_key.pem 파일에서 PrivateKey 로드
     */
    private PrivateKey loadPrivateKey(Resource resource) {
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String keyData = reader.lines()
                    .filter(line -> !line.startsWith("-----BEGIN") && !line.startsWith("-----END"))
                    .collect(Collectors.joining());

            byte[] decodedKey = Base64.getDecoder().decode(keyData);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            log.error("Private key 로드 실패", e);
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Access Token 생성 (Long userId)
     */
    public String generateAccessToken(Long userId, String role) {
        return generateToken(userId.toString(), role, accessTokenExpiration);
    }

    /**
     * Refresh Token 생성 (Long userId)
     */
    public String generateRefreshToken(Long userId, String role) {
        return generateToken(userId.toString(), role, refreshTokenExpiration);
    }

    /**
     * JWT 토큰 생성
     */
    private String generateToken(String userId, String role, long expirationTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(privateKey)
                .compact();
    }

    /**
     * Bearer 접두사 제거
     */
    public String extractTokenFromBearer(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(JwtConstants.BEARER_PREFIX)) {
            return bearerToken.substring(JwtConstants.BEARER_PREFIX_LENGTH);
        }
        return bearerToken;
    }
}
