package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.constant.RoleUser;
import com.theo.cafe_cashier.entity.UserRole;
import org.springframework.stereotype.Service;

@Service
public interface UserRoleService {
    UserRole getOrSaveUserRole(RoleUser roleUser);
}
