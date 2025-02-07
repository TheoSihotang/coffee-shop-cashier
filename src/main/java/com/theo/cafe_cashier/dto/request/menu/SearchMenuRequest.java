package com.theo.cafe_cashier.dto.request.menu;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMenuRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String query;
}
