package com.theo.cafe_cashier.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theo.cafe_cashier.dto.response.utils.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CommonResponse<?> res = CommonResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(accessDeniedException.getMessage())
                .build();
        String stringResponse = objectMapper.writeValueAsString(res);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(stringResponse);
    }
}
