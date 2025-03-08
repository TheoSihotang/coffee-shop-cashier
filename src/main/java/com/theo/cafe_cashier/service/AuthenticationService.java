package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.dto.request.Authentication.LoginRequest;
import com.theo.cafe_cashier.dto.request.Authentication.RegisterRequest;
import com.theo.cafe_cashier.dto.response.LoginResponse;
import com.theo.cafe_cashier.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
