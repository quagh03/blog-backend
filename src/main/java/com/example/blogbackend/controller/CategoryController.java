package com.example.blogbackend.controller;

import com.example.blogbackend.entity.Category;
import com.example.blogbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        try{
            List<Category> categoryList = categoryService.getAllCategories();
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{categoryid}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryid){
        try {
            Category foundCategory = categoryService.getCategoryById(categoryid);
            return new ResponseEntity<>(foundCategory, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category categoryToSave){
        try {
            categoryService.saveCategory(categoryToSave);
            return new ResponseEntity<>("Da them moi danh muc",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{categoryid}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryid){
        try {
            categoryService.deleteCategory(categoryid);
            return new ResponseEntity<>("Da xoa danh muc id: " + categoryid, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{categoryid}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryid, @RequestBody Category category){
        try {
            categoryService.editCategory(categoryid, category);
            return new ResponseEntity<>("Da cap nhat danh muc", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
