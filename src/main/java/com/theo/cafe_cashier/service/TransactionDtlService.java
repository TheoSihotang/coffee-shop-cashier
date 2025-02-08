package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.entity.TransactionDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TransactionDtlService {
    List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails);
    List<TransactionDetail> getAll();
}
