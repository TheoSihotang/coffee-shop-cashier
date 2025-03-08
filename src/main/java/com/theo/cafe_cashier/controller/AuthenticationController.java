package com.theo.cafe_cashier.controller;

import com.theo.cafe_cashier.constant.ApiUrl;
import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.request.Authentication.LoginRequest;
import com.theo.cafe_cashier.dto.request.Authentication.RegisterRequest;
import com.theo.cafe_cashier.dto.response.LoginResponse;
import com.theo.cafe_cashier.dto.response.RegisterResponse;
import com.theo.cafe_cashier.dto.response.utils.CommonResponse;
import com.theo.cafe_cashier.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.AUTHENTICATION_API)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        RegisterResponse register = service.register(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_REGISTRATION)
                .data(register)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<LoginResponse>> login (@RequestBody LoginRequest request) {
        LoginResponse login = service.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_LOGIN)
                .data(login)
                .build();
        return ResponseEntity.ok(response);
    }
}
