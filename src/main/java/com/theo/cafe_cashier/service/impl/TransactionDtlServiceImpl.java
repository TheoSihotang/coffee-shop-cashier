package com.theo.cafe_cashier.service.impl;

import com.theo.cafe_cashier.entity.TransactionDetail;
import com.theo.cafe_cashier.repository.TransactionDetailRepository;
import com.theo.cafe_cashier.service.TransactionDtlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDtlServiceImpl implements TransactionDtlService {
    private final TransactionDetailRepository repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return repository.saveAllAndFlush(transactionDetails);
    }
}
