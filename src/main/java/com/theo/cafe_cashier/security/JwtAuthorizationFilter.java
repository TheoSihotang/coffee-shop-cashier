package com.theo.cafe_cashier.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theo.cafe_cashier.dto.response.JwtClaims;
import com.theo.cafe_cashier.entity.UserAccount;
import com.theo.cafe_cashier.service.JwtService;
import com.theo.cafe_cashier.service.UserAccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserAccountService accountService;
    final String AUTH_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader(AUTH_HEADER);
            if (token != null && jwtService.verifyJwt(token)) {
                JwtClaims claimsByToken = jwtService.getClaimsByToken(token);
                UserAccount account = accountService.getUserAccountById(claimsByToken.getAccountId());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        account.getEmail(),
                        null,
                        account.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.info("Can't set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
