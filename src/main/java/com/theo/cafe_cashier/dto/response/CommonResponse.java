package com.theo.cafe_cashier.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String message;
    private String statusCode;
    private T data;
    private PagingResponse paging;
}
