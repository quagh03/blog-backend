package com.example.blogbackend.service;

import com.example.blogbackend.entity.Category;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Category getCategoryById(Long id){
        try {
            Optional<Category> foundCategory = categoryRepository.findById(id);
            return foundCategory.get();
        }catch (Exception e){
            throw new CustomException("Error",e);
        }
    }

    @Override
    public void saveCategory(Category categoryToSave){
        try {
            categoryRepository.save(categoryToSave);
        }catch (Exception e){
            throw new CustomException("Loi khi them moi danh muc",e);
        }
    }

    @Override
    public void deleteCategory(Long categoryid){
        try {
            categoryRepository.deleteById(categoryid);
        }catch (Exception e){
            throw new CustomException("Loi khi xoa danh muc", e);
        }
    }

    @Override
    public void editCategory(Long categoryid, Category newCategory) throws Exception {
        try{
            Optional<Category> existCategory = categoryRepository.findById(categoryid);
            if(existCategory.isPresent()){
                Category categoryToEdit = existCategory.get();
                BeanUtils.copyProperties(newCategory, categoryToEdit, "id");
                categoryRepository.save(categoryToEdit);
            }
        }catch (Exception e){
            throw new Exception("Error: ", e);
        }
    }
}
