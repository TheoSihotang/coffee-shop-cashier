package com.theo.cafe_cashier.service.impl;

import com.theo.cafe_cashier.constant.RoleUser;
import com.theo.cafe_cashier.dto.request.Authentication.LoginRequest;
import com.theo.cafe_cashier.dto.request.Authentication.RegisterRequest;
import com.theo.cafe_cashier.dto.response.LoginResponse;
import com.theo.cafe_cashier.dto.response.RegisterResponse;
import com.theo.cafe_cashier.entity.UserAccount;
import com.theo.cafe_cashier.entity.UserRole;
import com.theo.cafe_cashier.repository.UserAccountRepository;
import com.theo.cafe_cashier.service.AuthenticationService;
import com.theo.cafe_cashier.service.JwtService;
import com.theo.cafe_cashier.service.UserAccountService;
import com.theo.cafe_cashier.service.UserRoleService;
import com.theo.cafe_cashier.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAccountRepository repository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ValidatorUtil validatorUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        try {
            validatorUtil.validate(request);
            String passwordEncode = passwordEncoder.encode(request.getPassword());
            UserRole role = userRoleService.getOrSaveUserRole(RoleUser.ROLE_CUSTOMER);
            UserAccount account = UserAccount.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncode)
                    .role(role)
                    .isEnabled(true)
                    .createdAt(new Date())
                    .updatedAt(null)
                    .build();

            repository.saveAndFlush(account);
            List<String> roles = account.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority()).toString()).toList();
            return RegisterResponse.builder()
                    .firstName(account.getFirstName())
                    .lastName(account.getLastName())
                    .email(account.getEmail())
                    .roles(roles)
                    .created_at(account.getCreatedAt())
                    .updated_at(account.getUpdatedAt())
                    .build();
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount account = (UserAccount) authenticate.getPrincipal();
        String token = jwtService.generateToken(account);

        return LoginResponse.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .role(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(token)
                .build();
    }
}
