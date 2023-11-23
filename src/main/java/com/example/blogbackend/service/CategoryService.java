package com.example.blogbackend.service;

import com.example.blogbackend.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    void saveCategory(Category categoryToSave);

    void deleteCategory(Long categoryid);

    void editCategory(Long categoryid, Category categoryToEdit) throws Exception;
}
