package com.example.blogbackend.service.impl;

import com.example.blogbackend.entity.Role;
import com.example.blogbackend.entity.User;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User existUser = user.get();
        Role userRole = existUser.getRoles();

        // Ensure that the user has a role
        if (userRole == null) {
            throw new UsernameNotFoundException("User does not have a role");
        }

        // You can customize the authorities based on your UserRole enum
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(userRole.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                existUser.getUsername(),
                existUser.getPasswordHash(),
                authorities
        );
    }
}
