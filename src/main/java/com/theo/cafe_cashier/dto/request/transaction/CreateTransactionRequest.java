package com.theo.cafe_cashier.dto.request.transaction;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    private List<TransactionDetailRequest> detailRequests;
}
