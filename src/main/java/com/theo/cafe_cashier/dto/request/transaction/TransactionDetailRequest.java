package com.theo.cafe_cashier.dto.request.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailRequest {
    @NotBlank(message = "Menu ID is required")
    private String menuId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than or equals 1")
    private Integer qty;
}
