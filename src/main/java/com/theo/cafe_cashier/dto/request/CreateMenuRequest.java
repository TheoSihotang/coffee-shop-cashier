package com.theo.cafe_cashier.dto.request;

import com.theo.cafe_cashier.entity.Image;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuRequest {
    @NotBlank(message = "Menu name is required")
    @Size(min = 3, message = "The menu name is to shor, min 3 characters")
    private String name;

    @NotNull(message = "Menu price is required")
    @Min(value = 1000, message = "Price must be greater than or equal 1000")
    private Long price;

    private String description;

    private MultipartFile image;
}
