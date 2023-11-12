package com.example.blogbackend.service;

import com.example.blogbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    Optional<User> getUserById(Long userid);
    Optional<User> getUserByUsername(String username);
    void deleteUser(Long userid);
    User addUser(User userToAdd);

    User updateUser(Long userid, User userToUpdate);
}
