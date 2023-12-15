package com.example.blogbackend.service;

import com.example.blogbackend.entity.Role;

import java.util.List;

public interface RoleService {
    Role getAllRolesForUser(Long userId);

    List<Role> getAllRolesOnSystem();

    Role addRole(Role roleToAdd);
}
