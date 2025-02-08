package com.theo.cafe_cashier.dto.request.transaction;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchTransactionRequest {
    private Long queue;
    private String date;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String query;
}
