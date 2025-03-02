package com.theo.cafe_cashier.service.impl;

import com.theo.cafe_cashier.constant.RoleUser;
import com.theo.cafe_cashier.entity.UserRole;
import com.theo.cafe_cashier.repository.UserRoleRepository;
import com.theo.cafe_cashier.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserRole getOrSaveUserRole(RoleUser roleUser) {
        return userRoleRepository.findByRole(roleUser).orElseGet(() -> userRoleRepository.saveAndFlush(UserRole.builder()
                        .role(roleUser)
                .build()));
    }
}
