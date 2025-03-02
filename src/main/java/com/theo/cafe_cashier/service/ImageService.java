package com.theo.cafe_cashier.service;

import com.theo.cafe_cashier.dto.response.ImageResponse;
import com.theo.cafe_cashier.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    Image create(MultipartFile file);
    Resource getImage(String id);
    void delete(String id);
}
