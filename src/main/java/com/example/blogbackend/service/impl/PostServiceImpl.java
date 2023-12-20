package com.example.blogbackend.service.impl;

import com.example.blogbackend.dto.PostDto;
import com.example.blogbackend.entity.Category;
import com.example.blogbackend.entity.Post;
import com.example.blogbackend.entity.PostCategory;
import com.example.blogbackend.entity.User;
import com.example.blogbackend.exceptionhandle.CustomException;
import com.example.blogbackend.repository.CategoryRepository;
import com.example.blogbackend.repository.PostCategoryRepository;
import com.example.blogbackend.repository.PostRepository;
import com.example.blogbackend.repository.UserRepository;
import com.example.blogbackend.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Post> getPostsByCategory(Long categoryid){
        try {
            Category category = categoryRepository.findById(categoryid)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục"));

            return postRepository.findByCategoryList(category);
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng danh mục " + categoryid ,e);
        }
    }

    @Override
    public List<Post> getPostNotPublised(){
        try {
            return postRepository.findByPublished(false);
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng chua publish" ,e);
        }
    }

    @Override
    public List<Post> getPostPublised(){
        try {
            return postRepository.findByPublished(true);
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng chua publish" ,e);
        }
    }

    @Override
    public List<Post> getPostsByAuthor(Long authorid){
        try {
            User user = userRepository.findById(authorid)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng"));

            return postRepository.findByAuthor(user);
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng danh mục " + authorid ,e);
        }
    }

    @Override
    public List<Post> getAllPosts(){
        try{
            return postRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Lỗi Khi lấy tất cả bài đăng",e);
        }
    }

    @Override
    public Optional<Post> getPostById(Long id){
        try {
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Post not found"));

            post.setViews(post.getViews() + 1);

            postRepository.save(post);

            return Optional.of(post);
        }catch (Exception e){
            throw new CustomException("Lỗi khi lấy bài đăng có Id: " + id, e);
        }
    }

    @Override
    public List<Post> searchByKeyword(String keyword){
        if (keyword == null) {
            throw new CustomException("Không có từ khóa để tìm kiếm");
        }
        try {
            List<Post> results = postRepository.search(keyword);
            if(results == null || results.isEmpty()){
                throw new CustomException("Không tìm thấy " + keyword);
            }
            return results;
        } catch (RuntimeException e){
            throw new CustomException("",e);
        }
    }

    @Override
    @Transactional
    public Post updatePost(Long postId, PostDto postDto){
        try {
            Post existPost = postRepository.findById(postId)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy bài viết với Id: " + postId));


            BeanUtils.copyProperties(postDto, existPost, "id", "categories","updatedAt", "createAt");

            postCategoryRepository.deleteInBatch(existPost.getCategories());

            entityManager.flush();
            entityManager.clear();

            List<Category> categories = categoryRepository.findAllById(postDto.getCategoryIds());
            if (categories.size() != postDto.getCategoryIds().size()) {
                throw new CustomException("One or more categories not found");
            }
            existPost.getCategories().clear();

            for (Category category : categories) {
                PostCategory postCategory = new PostCategory(existPost, category);
                existPost.getCategories().add(postCategory);
            }
            return postRepository.save(existPost);

        }catch (Exception e){
            throw new CustomException("Lỗi khi cập nhật bài viết", e);
        }
    }

    @Override
    @Transactional
    public Post addPost(PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post, "id", "categoryIds");

        User author = userRepository.findById(postDto.getAuthorId())
                .orElseThrow(() -> new CustomException("Không có người dùng với id " + postDto.getAuthorId()));
        post.setAuthor(author);

        if(postDto.getParentId() != null){
            Post parentPost = postRepository.findById(postDto.getParentId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy bài đăng với id " + postDto.getParentId()));
            post.setParent(parentPost);
        }

        List<Category> categories = categoryRepository.findAllById(postDto.getCategoryIds());
        if(categories.size() != postDto.getCategoryIds().size()){
            throw new CustomException("Một hoặc nhiều hơn danh mục không tìm thấy");
        }

        for(Category category : categories){
            PostCategory postCategory = new PostCategory(post, category);
            post.getCategories().add(postCategory);
        }

        try{
            return postRepository.save(post);
        }catch (DataIntegrityViolationException e){
            throw new CustomException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deletePost(Long id){
        try {
            Post existPost = postRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thầy bài viết với Id: " + id));
            postRepository.delete(existPost);
        }catch (Exception e){
            throw new CustomException("Lỗi khi xoá bài viết", e);
        }
    }
}
