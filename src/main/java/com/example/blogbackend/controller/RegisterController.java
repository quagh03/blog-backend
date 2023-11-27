package com.example.blogbackend.controller;

import com.example.blogbackend.entity.User;
import com.example.blogbackend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerFunction(@RequestBody User registerUser){
        try {
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
