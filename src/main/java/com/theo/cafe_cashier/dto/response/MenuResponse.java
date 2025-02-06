package com.theo.cafe_cashier.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private String id;
    private String name;
    private Long price;
    private Boolean readyOrNot;
    private String description;
    private ImageResponse image;
}
