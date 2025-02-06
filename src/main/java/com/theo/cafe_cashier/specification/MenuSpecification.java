package com.theo.cafe_cashier.specification;

import com.theo.cafe_cashier.entity.Menu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuSpecification {
    public static Specification<Menu> getSpecification(String q){
        return (root, query, criteriaBuilder) -> {
            if(!StringUtils.hasText(q)) return criteriaBuilder.conjunction();
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")), "%" + q.toLowerCase() + "%"
                    )
            );

            try {
                Integer price = Integer.valueOf(q);
                predicates.add(criteriaBuilder.equal(
                        root.get("price"), price
                ));
            } catch (Exception e) {
                //ignore
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[]{}));
        };
    }
}
