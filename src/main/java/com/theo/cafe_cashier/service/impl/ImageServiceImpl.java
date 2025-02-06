package com.theo.cafe_cashier.service.impl;

import com.theo.cafe_cashier.constant.ResponseMessage;
import com.theo.cafe_cashier.dto.response.ImageResponse;
import com.theo.cafe_cashier.entity.Image;
import com.theo.cafe_cashier.repository.ImageRepository;
import com.theo.cafe_cashier.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final Path filePath;

    public ImageServiceImpl(ImageRepository imageRepository, @Value("${cashier_app.multipart.path-location}") Path filePath) {
        this.imageRepository = imageRepository;
        this.filePath = filePath;
    }

    @PostConstruct
    public void initDirectory(){
        if(!Files.exists(filePath)){
            try {
                Files.createDirectories(filePath);
            }catch (IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Image create(MultipartFile file) {
        try {
            if(!List.of("image/jpeg", "image/png", "image/jpg").contains(file.getContentType()))
                throw new ConstraintViolationException(ResponseMessage.ERROR_INVALID_IMAGE_TYPE, null);
            String uniqueName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = filePath.resolve(uniqueName);

            Files.copy(file.getInputStream(), path);

            Image image = Image.builder()
                    .path(path.toString())
                    .size(file.getSize())
                    .name(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();
            imageRepository.saveAndFlush(image);
            return image;
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getImage(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path path = Paths.get(image.getPath());
            if(!Files.exists(path)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            }
            return new UrlResource(path.toUri());
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path path = Paths.get(image.getPath());
            if(!Files.exists(path)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            }
            Files.delete(path);
            imageRepository.delete(image);
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
