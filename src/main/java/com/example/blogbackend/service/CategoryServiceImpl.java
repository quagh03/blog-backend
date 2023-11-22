package com.example.blogbackend.service;

import com.example.blogbackend.entity.Category;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
        try {
            return categoryRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả danh mục",e);
        }
    }


}
