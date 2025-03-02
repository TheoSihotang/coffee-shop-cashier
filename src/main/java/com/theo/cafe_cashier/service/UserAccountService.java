package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserAccountService extends UserDetailsService {
    UserAccount getUserAccountById(String id);
    UserAccount getByContext();
}
