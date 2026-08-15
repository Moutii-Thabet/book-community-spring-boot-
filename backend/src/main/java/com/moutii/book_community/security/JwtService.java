package com.moutii.book_community.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final static String TOKEN_TYPE = "token_type";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Value("${app.security.jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${app.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public JwtService() throws Exception {
        this.privateKey = KeyUtils.getSecretKey("keys/local-only/private_key.pem");
        this.publicKey = KeyUtils.getPublicKey("keys/local-only/public_key.pem");
    }

    public String generateAccessToken(final String username) {
        Map<String,Object> claims = Map.of(TOKEN_TYPE,"ACCESS_TOKEN");
        return buildToken(username,this.accessTokenExpiration,claims);
    }

    public String generateRefreshToken(final String username) {
        Map<String,Object> claims = Map.of(TOKEN_TYPE,"REFRESH_TOKEN");
        return buildToken(username,this.refreshTokenExpiration,claims);
    }

    private String buildToken(final String username, final long expiration, Map<String,Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(privateKey)
                .compact();
    }

    public boolean validateToken(final String token, final String expectedUsername) {
        String username = extractUsername(token);
        return username.equals(expectedUsername) && !tokenIsExpired(token);
    }

    public String extractUsername(final String token) {
        return extractClaims(token).getSubject();
    }

    public boolean tokenIsExpired(final String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String refreshAccessToken(final String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        if(!claims.get(TOKEN_TYPE).equals("REFRESH_TOKEN")) {
            throw new RuntimeException("Invalid token type");
        }
        if(tokenIsExpired(refreshToken)) {
            throw new RuntimeException("Token is Expired");
        }
        String username = extractUsername(refreshToken);
        return generateAccessToken(username);
    }

    private Claims extractClaims(final String token) {

        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid jwt token",e);
        }
    }




}
