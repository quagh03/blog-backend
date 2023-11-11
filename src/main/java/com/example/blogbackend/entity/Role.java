package com.example.blogbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    public Role() {
        // Default constructor
    }

    public Role(User user, UserRole role) {
        this.user = user;
        this.role = role;
    }

    // Getters and setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Enum UserRole definition
    public enum UserRole {
        ROLE_ADMIN,
        ROLE_AUTHOR,
        ROLE_GUEST
    }

    public static class RoleId implements Serializable {

        private Long user;
        private String role;

        public RoleId() {
            // Default constructor
        }

        public RoleId(Long user, String role) {
            this.user = user;
            this.role = role;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RoleId roleId = (RoleId) o;
            return Objects.equals(user, roleId.user) &&
                    Objects.equals(role, roleId.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, role);
        }
    }
}
