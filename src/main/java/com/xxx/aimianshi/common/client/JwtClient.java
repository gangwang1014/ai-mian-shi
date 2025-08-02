package com.xxx.aimianshi.common.client;

import com.xxx.aimianshi.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class JwtClient {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 JWT Token
     *
     * @param subject    token的主题（比如用户标识）
     * @param claimKey   自定义声明的键
     * @param claimValue 自定义声明的值
     * @return 生成的 JWT token 字符串
     * @author by 红叶已尽秋
     */
    public String createToken(String subject, String claimKey, Object claimValue) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getTtl());
        return Jwts.builder()
                .issuedAt(now)
                .expiration(expiration)
                .subject(subject)
                .claim(claimKey, claimValue)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 生成 JWT Token
     *
     * @param claimKey   自定义声明的键
     * @param claimValue 自定义声明的值
     * @return 生成的 JWT token 字符串
     * @author by 红叶已尽秋
     */
    public String createToken(String claimKey, Object claimValue) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getTtl());
        return Jwts.builder()
                .issuedAt(now)
                .expiration(expiration)
                .subject(claimKey)
                .claim(claimKey, claimValue)
                .signWith(secretKey)
                .compact();
    }

    public String createToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getTtl());
        return Jwts.builder()
                .issuedAt(now)
                .expiration(expiration)
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析 JWT Token 获取 Claims
     *
     * @param token JWT token 字符串
     * @return token中的 Claims
     * @author by 红叶已尽秋
     */
    public Claims parseJWT(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 获取 JWT Token 的签发时间
     *
     * @param token JWT token 字符串
     * @return 签发时间
     * @author by 红叶已尽秋
     */
    public Date getIssuedAt(String token) {
        return parseJWT(token).getIssuedAt();
    }
}