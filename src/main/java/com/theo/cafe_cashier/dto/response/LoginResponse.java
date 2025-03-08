package com.theo.cafe_cashier.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private List<String> role;
}
