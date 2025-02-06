package com.theo.cafe_cashier.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private String url;
    private String name;
}
