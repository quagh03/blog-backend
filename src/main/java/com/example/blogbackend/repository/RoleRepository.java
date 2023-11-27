package com.example.blogbackend.repository;

import com.example.blogbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Role.RoleId> {
    List<Role> findAllByUserId(Long userId);
}
