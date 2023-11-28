package com.example.blogbackend.service;

import com.example.blogbackend.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ImageService {
    List<Image> getAllImage();

    String uploadImage(MultipartFile file) throws IOException;

    String deleteImageByName(String uploadedURL);

    Resource loadImage(String imageName) throws MalformedURLException;
}
