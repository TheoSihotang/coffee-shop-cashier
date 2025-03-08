package com.theo.cafe_cashier.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private Date created_at;
    private Date updated_at;
}
