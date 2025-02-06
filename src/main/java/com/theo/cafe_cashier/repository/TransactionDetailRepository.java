package com.theo.cafe_cashier.repository;

import com.theo.cafe_cashier.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String>, JpaSpecificationExecutor<TransactionDetail> {
}
