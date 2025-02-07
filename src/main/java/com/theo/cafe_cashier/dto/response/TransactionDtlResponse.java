package com.theo.cafe_cashier.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtlResponse {
    private String trxDtlId;
    private Integer quantity;
    private String menuName;
    private Long menuPrice;
}
