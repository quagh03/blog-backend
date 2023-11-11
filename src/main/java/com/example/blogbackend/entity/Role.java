package com.example.blogbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(
        name = "role",
        indexes = {@Index(name = "idx_role_user", columnList = "user_id")},
        uniqueConstraints = {@UniqueConstraint(name = "uq_role_user", columnNames = {"user_id", "role"})}
)
@IdClass(Role.RoleId.class)
public class Role {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    // Constructors, getters, and setters

    public static class RoleId implements Serializable {

        private Long user;

        private String role;

        public RoleId() {
        }

        public RoleId(Long user, String role) {
            this.user = user;
            this.role = role;
        }

        // Equals and hashCode methods (make sure to implement them)

        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user = user;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    // Enum UserRole definition
    public enum UserRole {
        ROLE_ADMIN,
        ROLE_AUTHOR,
        ROLE_GUEST
    }
}

