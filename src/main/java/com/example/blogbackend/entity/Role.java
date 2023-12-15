package com.example.blogbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(
        name = "role",
        indexes = {@Index(name = "idx_role_user", columnList = "user_id")},
        uniqueConstraints = {@UniqueConstraint(name = "uq_role_user", columnNames = {"user_id", "role"})}
)
//TRÁNH VÒNG LẶP VÔ TẬN KHI SERIALIZE ĐẦY ĐỦ THÔNG TIN
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false, unique = true)
//    @JsonBackReference
    private User user;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

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
}
