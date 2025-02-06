package com.theo.cafe_cashier.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.theo.cafe_cashier.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = TableConstant.TABLE_TRANSACTION)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "date_trx")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private Date date;

    @Column(name = "queue_number")
    private Long queueNumber;

    @OneToMany(mappedBy = "transactionId") // mapped by digunakan jika ingin koneksi dua arah
    private List<TransactionDetail> transactionDetails;
}
