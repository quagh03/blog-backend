package com.example.blogbackend.service;

import com.example.blogbackend.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> getAllImage();

    String uploadImage(MultipartFile file) throws IOException;
}
