package com.theo.cafe_cashier.controller;

import com.theo.cafe_cashier.constant.ApiUrl;
import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.request.transaction.CreateTransactionRequest;
import com.theo.cafe_cashier.dto.response.TransactionResponse;
import com.theo.cafe_cashier.dto.response.utils.CommonResponse;
import com.theo.cafe_cashier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ApiUrl.TRANSACTION_API)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody CreateTransactionRequest request){
        TransactionResponse transaction = transactionService.save(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.name())
                .message(ResponseMessage.SUCCESS_CREATE_NEW_TRANSACTION)
                .data(transaction)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
