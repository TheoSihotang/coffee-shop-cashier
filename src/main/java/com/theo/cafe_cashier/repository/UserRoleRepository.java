package com.theo.cafe_cashier.repository;

import com.theo.cafe_cashier.constant.RoleUser;
import com.theo.cafe_cashier.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByRole(RoleUser role);
}
