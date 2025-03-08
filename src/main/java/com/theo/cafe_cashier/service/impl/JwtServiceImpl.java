package com.theo.cafe_cashier.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.response.JwtClaims;
import com.theo.cafe_cashier.entity.UserAccount;
import com.theo.cafe_cashier.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final String SECRET_KEY;
    private final String JWT_ISSUER;
    private final Long EXPIRATION_TOKEN;

    public JwtServiceImpl(@Value(value = "${cashier.app.jwt.secret.key}") String secretKey,
                          @Value(value = "${cashier.app.jwt.issuer}") String jwtIssuer,
                          @Value(value = "${cashier.app.jwt.expirationInSecond}") Long expirationToken)
    {
        SECRET_KEY = secretKey;
        JWT_ISSUER = jwtIssuer;
        EXPIRATION_TOKEN = expirationToken;
    }

    @Override
    public String generateToken(UserAccount userAccount) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            return JWT.create()
                    .withSubject(userAccount.getId())
                    .withClaim("roles", userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(EXPIRATION_TOKEN))
                    .withIssuer(JWT_ISSUER)
                    .sign(algorithm);

        } catch (JWTCreationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR_CREATE_TOKEN);
        }
    }

    @Override
    public Boolean verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            jwtVerifier.verify(parseToken(token));
            return true;
        } catch (JWTVerificationException e){
            log.info("Invalid JWT signature/claims : {} ", e.getMessage());
            return null;
        }
    }

    @Override
    public JwtClaims getClaimsByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            DecodedJWT verify = jwtVerifier.verify(parseToken(token));
            return JwtClaims.builder()
                    .accountId(verify.getSubject())
                    .roles(verify.getClaim("roles").asList(String.class))
                    .build();
        } catch (JWTVerificationException e) {
            log.info("Invalid JWT signature/claims : {} ", e.getMessage());
            return null;
        }
    }

    public String parseToken(String token){
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }
}
