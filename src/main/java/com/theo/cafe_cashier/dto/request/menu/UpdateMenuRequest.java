package com.theo.cafe_cashier.dto.request.menu;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMenuRequest {
    private String id;

    @NotBlank(message = "Menu name is required")
    @Size(min = 3, message = "The menu name is to shor, min 3 characters")
    private String name;

    @NotNull(message = "Menu price is required")
    @Min(value = 1000, message = "Price must be greater than or equal 1000")
    private Long price;

    private String description;

    private MultipartFile image;
}
