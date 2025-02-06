package com.theo.cafe_cashier.repository;

import com.theo.cafe_cashier.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
