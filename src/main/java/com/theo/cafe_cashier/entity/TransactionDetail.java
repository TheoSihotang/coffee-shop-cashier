package com.theo.cafe_cashier.entity;

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

    @Column(name = "total_price")
    private Long totalPrice;

    @OneToMany
    @JoinColumn(name = "menu_id", nullable = false)
    private List<Menu> menuId;

    @ManyToOne
    @JoinColumn(name = "trx_id")
    private Transaction transactionId;
}
