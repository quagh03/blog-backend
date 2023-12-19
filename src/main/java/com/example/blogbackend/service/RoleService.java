package com.example.blogbackend.service;

import com.example.blogbackend.entity.Role;
import jakarta.transaction.Transactional;

import java.util.List;

public interface RoleService {
    Role getAllRolesForUser(Long userId);

    List<Role> getAllRolesOnSystem();

    Role addRole(Role roleToAdd);

    @Transactional
    Role updateRole(Long roleId, Role roleToUpdate);

    void deleteRole(Long roleId);
}
