package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.CategoryForm;
import com.spring.ecommerce.lvtn.model.Entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(String id);
    Optional<Category> findCategoryBySlug(String slug);
    Category createCategory(CategoryForm categoryForm);
    Category updateCategory(String id, CategoryForm categoryForm);

    void setActivateCategory(String categoryId, boolean isActive);
    void deleteCategory(String categoryId);

}
