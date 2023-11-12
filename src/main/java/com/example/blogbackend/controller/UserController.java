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
    public ResponseEntity<?> addUser(@RequestBody User userToAdd){
        try {
            User addedUser =  userService.addUser(userToAdd);
            return new ResponseEntity<>("Đã thêm người dùng:\n" + addedUser,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Lỗi: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userid){
        try {
            userService.deleteUser(userid);
            return new ResponseEntity<>("Đã xóa người dùng với Id: " + userid, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() + userid, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{userid}")
    public ResponseEntity<?> updateUser(@PathVariable Long userid, @RequestBody User newUser){
        try {
            userService.updateUser(userid, newUser);
            return new ResponseEntity<>("Đã cập nhật người dùng có Id: " + userid, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() + userid, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
