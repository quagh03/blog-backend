package com.example.blogbackend.controller;

import com.example.blogbackend.entity.User;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        try {
            List<User> users = userService.getAllUser();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getUserById(@PathVariable Long userid){
        try {
            User user = userService.getUserById(userid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với ID: " + userid));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("username={username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        try {
            User user = userService.getUserByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với Username: " + username));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (ResponseStatusException e){
            throw e;
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(User userToAdd){
        //needed to be compelete
        return null;
    }

    @DeleteMapping("{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userid){
        //needed to be compelete
        return null;
    }

    @PostMapping("{userid}")
    public ResponseEntity<?> updateUser(@PathVariable Long userid, User newUser){
        //needed to be compelete
        return null;
    }
}
