package com.theo.cafe_cashier.dto.request.Authentication;

import com.theo.cafe_cashier.constant.RequestMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = RequestMessage.EMAIL_OR_PASSWORD_REQUIRED)
    @Size(min = 6, message = RequestMessage.MIN_LENGTH_USERNAME_AND_PASSWORD)
    private String email;

    @NotBlank(message = RequestMessage.EMAIL_OR_PASSWORD_REQUIRED)
    @Size(min = 6, message = RequestMessage.MIN_LENGTH_USERNAME_AND_PASSWORD)
    private String password;
}
