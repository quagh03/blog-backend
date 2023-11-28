package com.example.blogbackend.service.impl;

import com.example.blogbackend.entity.Image;
import com.example.blogbackend.repository.ImageRepository;
import com.example.blogbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    private final Path root = Paths.get("uploads");

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

            String filename = Objects.requireNonNull(file.getOriginalFilename());
            Path fileNameAndPath = uploadDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.write(fileNameAndPath, file.getBytes());
            String uploadedURL = fileNameAndPath.toString();

            Image image = new Image(uploadedURL, filename);
            imageRepository.save(image);

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while uploading the image: " + e.getMessage();
        }
    }

    @Override
    public String deleteImageByName(String filename){
        try {
            Image image = imageRepository.findByFilename(filename);

            if(image!=null){
                Path imagePath = Paths.get(image.getUrl());
                Files.deleteIfExists(imagePath);
                imageRepository.delete(image);
            }

            return "Image deleted successfully!";
        }catch (Exception e){
            e.printStackTrace();
            return "Error occurred while deleting the image: " + e.getMessage();
        }
    }

    @Override
    public Resource loadImage(String imageName) throws MalformedURLException {
        Path file = root.resolve(imageName);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }
}
