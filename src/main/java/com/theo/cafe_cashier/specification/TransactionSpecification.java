package com.theo.cafe_cashier.specification;

import com.theo.cafe_cashier.dto.request.transaction.SearchTransactionRequest;
import com.theo.cafe_cashier.entity.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> getSpecification(SearchTransactionRequest request){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (request.getQueue() != null) {
                Predicate queueNumber = criteriaBuilder.equal(root.get("queueNumber"), request.getQueue());
                predicates.add(queueNumber);
            }

            if (request.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date tempDate = new Date();
                try {
                    tempDate = sdf.parse(request.getDate());
                } catch (ParseException e){
                    throw new RuntimeException(e);
                }
                Predicate date = criteriaBuilder.equal(root.get("date"), tempDate);
                predicates.add(date);
            }
            return query.where((predicates.toArray(new Predicate[]{}))).getRestriction();
        };
    }
}
