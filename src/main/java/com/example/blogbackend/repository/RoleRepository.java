package com.example.blogbackend.repository;

import com.example.blogbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Role.RoleId> {
}
