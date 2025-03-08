package com.theo.cafe_cashier.controller;

import com.theo.cafe_cashier.constant.ApiUrl;
import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.request.transaction.CreateTransactionRequest;
import com.theo.cafe_cashier.dto.request.transaction.SearchTransactionRequest;
import com.theo.cafe_cashier.dto.response.TransactionResponse;
import com.theo.cafe_cashier.dto.response.utils.CommonResponse;
import com.theo.cafe_cashier.dto.response.utils.PagingResponse;
import com.theo.cafe_cashier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_CREATE_NEW_TRANSACTION)
                .data(transaction)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getTransaction(
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "queueNumber", required = false) Long queueNumber,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name =  "sortBy", defaultValue = "queueNumber") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "q", required = false) String query
    ){
        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .page(page)
                .size(size)
                .date(date)
                .queue(queueNumber)
                .sortBy(sortBy)
                .direction(direction)
                .query(query)
                .build();
        Page<TransactionResponse> all = transactionService.findAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(all.getPageable().getPageNumber() + 1)
                .size(all.getPageable().getPageSize())
                .totalPage(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .hasNext(all.hasNext())
                .hasPrevious(all.hasPrevious())
                .build();
        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_ALL_DATA)
                .data(all.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
