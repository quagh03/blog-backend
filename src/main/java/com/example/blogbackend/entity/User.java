package com.example.blogbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "registered_at", nullable = false)
    private Date registeredAt;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "intro", length = 255)
    private String intro;

    @Column(name = "profile", columnDefinition = "TEXT")
    private String profile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    @JsonManagedReference được xem như là thành phần chính trong mối quan hệ trong serialization, vì vậy chúng sẽ được serialized bình thường.
//    @JsonBackReference được xem như phần phụ trợ, và nó sẽ bị lược bỏ trong serialization để tránh tình trạng lặp vô tận.
    private Role roles;

    public User() {
    }

    public User(String firstName, String lastName, String username, String mobile, String email, String passwordHash, Date registeredAt, Date lastLogin, String intro, String profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.passwordHash = passwordHash;
        this.registeredAt = registeredAt;
        this.lastLogin = lastLogin;
        this.intro = intro;
        this.profile = profile;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
