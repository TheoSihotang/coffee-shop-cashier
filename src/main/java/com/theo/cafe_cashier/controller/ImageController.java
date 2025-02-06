package com.theo.cafe_cashier.controller;

import com.theo.cafe_cashier.constant.ApiUrl;
import com.theo.cafe_cashier.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping(path = ApiUrl.MENU_IMAGE_DOWNLOAD_API + "{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String id){
        Resource resource = imageService.getImage(id);
        String headerValue = String.format("attachment; filename=%s", resource.getFilename());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
