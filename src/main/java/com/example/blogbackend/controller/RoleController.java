package com.example.blogbackend.controller;

import com.example.blogbackend.entity.Role;
import com.example.blogbackend.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/admin/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/{userid}")
    public ResponseEntity<?> getAllRolesForUser(@PathVariable Long userid){
        try {
            return new ResponseEntity<>(roleService.getAllRolesForUser(userid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles(){
        try {
            return new ResponseEntity<>(roleService.getAllRolesOnSystem(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody Role roleToAdd){
        try {
            return new ResponseEntity<>(roleService.addRole(roleToAdd), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("ERROR" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
