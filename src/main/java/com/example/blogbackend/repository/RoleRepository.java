package com.example.blogbackend.repository;

import com.example.blogbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserId(Long userId);
}
