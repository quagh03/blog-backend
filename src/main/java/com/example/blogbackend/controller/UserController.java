package com.example.blogbackend.controller;

import com.example.blogbackend.dto.UserDto;
import com.example.blogbackend.entity.Role;
import com.example.blogbackend.entity.User;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.jwt.JwtUtil;
import com.example.blogbackend.service.UserService;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog/users")
public class UserController {
    @Autowired
    private UserService userService;

    //ĐĂNG KÝ
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userToAdd){
        try {
            userToAdd.setPasswordHash(new BCryptPasswordEncoder().encode(userToAdd.getPasswordHash()));
            userToAdd.setRegisteredAt(new Date());
            User addedUser = userService.addUser(userToAdd);
            return new ResponseEntity<>("Đã đăng ký người dùng:\n" + addedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ĐĂNG NHẬP
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userToLogin){
        try {
            User user = userService.getUserByUsername(userToLogin.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với Username: " + userToLogin.getUsername()));
            if (!new BCryptPasswordEncoder().matches(userToLogin.getPasswordHash(), user.getPasswordHash())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mật khẩu không chính xác");
            }
            String token = JwtUtil.generateToken(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ADMIN LIST RA DANH SÁCH NGƯỜI DÙNG
    @GetMapping("/admin")
    public ResponseEntity<?> getAllUser(){
        try {
            List<User> users = userService.getAllUser();
            List<UserDto> displayList = users.stream().map(user -> {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                return userDto;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(displayList, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //LẤY NGƯỜI DÙNG THEO ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với ID: " + id));
            UserDto userToDisplay = new UserDto();
            BeanUtils.copyProperties(user, userToDisplay);
            return new ResponseEntity<>(userToDisplay, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //LẤY THÔNG TIN NGƯỜI DÙNG ĐANG ĐĂNG NHẬP
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Optional<User> user = userService.getUserByUsername(username);

            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //XÓA NGƯỜI DÙNG
    @DeleteMapping("/admin/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Deleted user with Id: " + id, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //SỬA THÔNG TIN NGƯỜI DÙNG
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_GUEST')")
    public ResponseEntity<?> updateUser(@RequestParam Long id, @RequestBody User newUser){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User currentUser = userService.getUserByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với Username: " + username));

            if (currentUser.getRoles().stream().anyMatch(role -> role.getRole().equals(Role.UserRole.ROLE_ADMIN)) || currentUser.getId().equals(id)) {
                newUser.setPasswordHash(new BCryptPasswordEncoder().encode(newUser.getPasswordHash()));
                userService.updateUser(id, newUser);
                return new ResponseEntity<>("Đã cập nhật người dùng có Id: " + id, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bạn không có quyền chỉnh sửa thông tin của người dùng này", HttpStatus.FORBIDDEN);
            }

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
