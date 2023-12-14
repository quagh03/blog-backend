package com.example.blogbackend.service.impl;

import com.example.blogbackend.entity.Role;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.RoleRepository;
import com.example.blogbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRolesForUser(Long userId) {
        try {
            return roleRepository.findAllByUserId(userId);
        }catch (Exception e){
            throw new CustomException("Error: ", e);
        }
    }

    @Override
    public List<Role> getAllRolesOnSystem(){
        try {
            return roleRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Error: ", e);
        }
    }
}
