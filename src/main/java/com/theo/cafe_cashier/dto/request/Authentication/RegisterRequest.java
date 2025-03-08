package com.theo.cafe_cashier.dto.request.Authentication;

import com.theo.cafe_cashier.constant.RequestMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = RequestMessage.IS_REQUIRED)
    @Size(min = 3, message = RequestMessage.MIN_LENGTH)
    private String firstName;

    @NotBlank(message = RequestMessage.IS_REQUIRED)
    @Size(min = 3, message = RequestMessage.MIN_LENGTH)
    private String lastName;

    @NotBlank(message = RequestMessage.IS_REQUIRED)
    @Email
    private String email;

    @NotBlank(message = RequestMessage.IS_REQUIRED)
    @Size(min = 6, message = RequestMessage.MIN_LENGTH_USERNAME_AND_PASSWORD)
    private String password;
}
