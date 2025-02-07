package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.dto.request.transaction.CreateTransactionRequest;
import com.theo.cafe_cashier.dto.request.transaction.SearchTransactionRequest;
import com.theo.cafe_cashier.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse save(CreateTransactionRequest request);
    Page<TransactionResponse> findAll(SearchTransactionRequest request);
    TransactionResponse findOne(String id);
}
