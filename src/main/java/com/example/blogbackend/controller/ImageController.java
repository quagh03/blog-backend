package com.example.blogbackend.controller;

import com.example.blogbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/display/{imageName}")
    public ResponseEntity<?> displayImage(@PathVariable String imageName) {
        try {
            Resource image = imageService.loadImage(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(image.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while displaying the image: " + e.getMessage());
        }
    }

    @DeleteMapping("/{imageName}")
    public ResponseEntity<?> deleteImage(@PathVariable String imageName){
        try {
            return new ResponseEntity<>(imageService.deleteImageByName(imageName), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting the image: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        try {
            String filename = imageService.uploadImage(file);

            return new ResponseEntity<>(filename, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading the image: " + e.getMessage());
        }
    }


}

