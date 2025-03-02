package com.theo.cafe_cashier.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theo.cafe_cashier.entity.TransactionDetail;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String trxId;
    private Long queueNumber;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private Date trxDate;
    private Long amount;
    private List<TransactionDtlResponse> trxDetails;
}
