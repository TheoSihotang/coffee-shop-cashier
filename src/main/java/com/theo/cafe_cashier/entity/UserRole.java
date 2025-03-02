package com.theo.cafe_cashier.entity;

import com.theo.cafe_cashier.constant.RoleUser;
import com.theo.cafe_cashier.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = TableConstant.TABLE_ROLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private RoleUser role;
}
