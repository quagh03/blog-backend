package com.example.blogbackend.service;

import com.example.blogbackend.entity.User;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser(){
        try{
            return userRepository.findAll();
        }catch (Exception e) {
            throw new CustomException("Lỗi khi lấy tất cả người dùng", e);
        }
    }

    @Override
    public Optional<User> getUserById(Long userid) {
        try {
            return Optional.ofNullable(userRepository.findById(userid)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với ID: " + userid)));
        } catch (Exception e) {
            throw new CustomException("Lỗi khi lấy người dùng theo ID", e);
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        try {
            return Optional.ofNullable(userRepository.findByUsername(username)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng với Username: " + username)));
        }catch (Exception e){
            throw new CustomException("Lỗi khi lấy người dùng theo Username", e);
        }
    }
}
