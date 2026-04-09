package com.cibertec.backend.qorifit.infraestructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;


    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(this.privateKey);
        this.verifier = JWT.require(algorithm)
                .withIssuer(this.userGenerator)
                .build();
    }

    public String createAccessToken(Authentication authentication, Long userId) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessTokenExpiration);

        String[] authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(authentication.getName())
                .withClaim("id", userId)
                .withArrayClaim("authorities", authorities)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withJWTId(UUID.randomUUID().toString())
                .sign(this.algorithm);
    }


    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        return this.verifier.verify(token);
    }

    public String[] getRawAuthorities(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("authorities").asArray(String.class);
    }

}