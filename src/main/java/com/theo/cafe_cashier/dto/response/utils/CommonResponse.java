package com.theo.cafe_cashier.dto.response.utils;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
    private PagingResponse paging;
}
