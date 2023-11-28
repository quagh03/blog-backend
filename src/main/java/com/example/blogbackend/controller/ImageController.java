package com.example.blogbackend.controller;

import com.example.blogbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/blog/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAllImage(){
        try{
            return new ResponseEntity<>(imageService.getAllImage(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }
}
