package com.example.blogbackend.controller;

import com.example.blogbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("{userid}")
    public ResponseEntity<?> getAllRole(@PathVariable Long userid){
        try {
            return new ResponseEntity<>(roleService.getAllRolesForUser(userid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }
}
