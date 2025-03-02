package com.theo.cafe_cashier.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.theo.cafe_cashier.constant.TableConstant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = TableConstant.TABLE_TRANSACTION_DETAIL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menuId;

    @Column(name = "menu_price")
    private Long menuPrice;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "trx_id")
    private Transaction transactionId;
}
