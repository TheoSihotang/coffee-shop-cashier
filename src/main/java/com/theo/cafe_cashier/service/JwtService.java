package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.dto.response.JwtClaims;
import com.theo.cafe_cashier.entity.UserAccount;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateToken(UserAccount userAccount);
    Boolean verifyJwt(String token);
    JwtClaims getClaimsByToken(String token);
}
