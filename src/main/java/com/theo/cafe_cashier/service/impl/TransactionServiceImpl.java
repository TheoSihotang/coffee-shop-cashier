package com.theo.cafe_cashier.service.impl;

import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.request.transaction.CreateTransactionRequest;
import com.theo.cafe_cashier.dto.request.transaction.SearchTransactionRequest;
import com.theo.cafe_cashier.dto.response.TransactionDtlResponse;
import com.theo.cafe_cashier.dto.response.TransactionResponse;
import com.theo.cafe_cashier.entity.Menu;
import com.theo.cafe_cashier.entity.Transaction;
import com.theo.cafe_cashier.entity.TransactionDetail;
import com.theo.cafe_cashier.entity.UserAccount;
import com.theo.cafe_cashier.repository.TransactionRepository;
import com.theo.cafe_cashier.service.MenuService;
import com.theo.cafe_cashier.service.TransactionDtlService;
import com.theo.cafe_cashier.service.TransactionService;
import com.theo.cafe_cashier.specification.TransactionSpecification;
import com.theo.cafe_cashier.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDtlService transactionDtlService;
    private final MenuService menuService;
    private final UserAccountServiceImpl userAccountService;
    private final ValidatorUtil validatorUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse save(CreateTransactionRequest request) {
        validatorUtil.validate(request);
        //get last record queue
        Transaction transactionLastRecord = transactionRepository.getQueue();
        long queue;
        if (transactionLastRecord == null) {
            queue = 1L;
        } else {
            queue = transactionLastRecord.getQueueNumber() + 1;
        }


        //Transaction
        UserAccount account = userAccountService.getByContext();
        UserAccount getAccount = userAccountService.getUserAccountById(request.getCustomerId());
        if (account.getId().equals(getAccount.getId())) {
            Transaction transaction = Transaction.builder()
                    .userAccountId(account)
                    .date(new Date())
                    .queueNumber(queue)
                    .build();

            //transactionDetail
            List<TransactionDetail> listTrxDtl = request.getDetailRequests().stream().map((details) -> {
                Menu menu = menuService.getOne(details.getMenuId());
                if (!menu.getReadyOrNot())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_MENU_NOT_READY);
                return TransactionDetail.builder()
                        .transactionId(transaction)
                        .qty(details.getQty())
                        .menuId(menu)
                        .menuPrice(menu.getPrice())
                        .build();
            }).toList();
            transaction.setTransactionDetails(listTrxDtl);
            transactionRepository.saveAndFlush(transaction);
            transactionDtlService.createBulk(listTrxDtl);

            List<TransactionDtlResponse> dtlResponses = listTrxDtl.stream().map((details) -> {
                return TransactionDtlResponse.builder()
                        .trxDtlId(details.getId())
                        .menuName(details.getMenuId().getName())
                        .quantity(details.getQty())
                        .menuPrice(details.getMenuPrice())
                        .build();
            }).toList();

            long amount = dtlResponses.stream().mapToLong(value -> value.getMenuPrice() * value.getQuantity()).sum();

            return TransactionResponse.builder()
                    .trxId(transaction.getId())
                    .customerId(transaction.getUserAccountId().getId())
                    .trxDate(transaction.getDate())
                    .queueNumber(transaction.getQueueNumber())
                    .amount(amount)
                    .trxDetails(dtlResponses)
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, ResponseMessage.ERROR_FORBIDDEN);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionResponse> findAll(SearchTransactionRequest request) {
        Specification<Transaction> specification = TransactionSpecification.getSpecification(request);
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Page<Transaction> all = transactionRepository.findAll(specification, pageable);
        return all.map(trx -> {
            long amount = trx.getTransactionDetails().stream().mapToLong(value -> value.getMenuPrice() * value.getQty()).sum();
            List<TransactionDtlResponse> responses = trx.getTransactionDetails().stream().map(details -> {
                return TransactionDtlResponse.builder()
                        .trxDtlId(details.getId())
                        .menuName(details.getMenuId().getName())
                        .menuPrice(details.getMenuPrice())
                        .quantity(details.getQty())
                        .build();
            }).toList();
            return TransactionResponse.builder()
                    .trxId(trx.getId())
                    .customerId(trx.getUserAccountId().getId())
                    .queueNumber(trx.getQueueNumber())
                    .trxDate(trx.getDate())
                    .trxDetails(responses)
                    .amount(amount)
                    .build();
        });
//        return new PageImpl<>(trxResponses, pageable, all.getTotalElements()); //bisa menggunakan cara ini juga
    }


    @Override
    public TransactionResponse findOne(String id) {
        return null;
    }
}
