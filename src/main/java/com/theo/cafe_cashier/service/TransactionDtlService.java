package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.entity.TransactionDetail;

import java.util.List;

public interface TransactionDtlService {
    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails);
}
