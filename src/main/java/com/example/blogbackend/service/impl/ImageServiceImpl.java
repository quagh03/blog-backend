package com.example.blogbackend.service.impl;

import com.example.blogbackend.entity.Image;
import com.example.blogbackend.repository.ImageRepository;
import com.example.blogbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getAllImage(){
        return imageRepository.findAll();
    }

    public String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @Override
    public String uploadImage(MultipartFile file) throws IOException{
        try {
            Path uploadDir = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path fileNameAndPath = uploadDir.resolve(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            String uploadedURL = fileNameAndPath.toString();

            Image image = new Image(uploadedURL);
            imageRepository.save(image);

            return fileNameAndPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while uploading the image: " + e.getMessage();
        }
    }
}
