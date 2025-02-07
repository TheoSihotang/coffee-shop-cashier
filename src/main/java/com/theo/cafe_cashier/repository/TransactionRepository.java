package com.theo.cafe_cashier.repository;

import com.theo.cafe_cashier.entity.Transaction;
import com.theo.cafe_cashier.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<TransactionDetail> {

    @Query(value = "select * from tb_trx order by queue_number DESC LIMIT 1", nativeQuery = true)
    Transaction getQueue();
}
