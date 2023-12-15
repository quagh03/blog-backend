package com.example.blogbackend.service.impl;

import com.example.blogbackend.entity.Role;
import com.example.blogbackend.entity.User;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.RoleRepository;
import com.example.blogbackend.repository.UserRepository;
import com.example.blogbackend.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Role getAllRolesForUser(Long userId) {
        try {
            return roleRepository.findByUserId(userId).get();
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

    @Override
    @Transactional
    public Role addRole(Role roleToAdd) {
        try {
            Long userId = roleToAdd.getUser().getId();
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User not found"));

            Optional<Role> existingRole = roleRepository.findByUserId(userId);
            if (existingRole.isPresent()) {
                throw new CustomException("User already has a role.");
            }

            roleToAdd.setUser(user);
            return roleRepository.save(roleToAdd);
        } catch (Exception e) {
            throw new CustomException("Error: " + e.getMessage());
        }
    }

}
