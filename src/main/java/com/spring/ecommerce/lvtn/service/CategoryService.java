package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.CategoryForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.CategoryProjection;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryProjection> findAll(int page, int size, String sortBy, String direction);
    Optional<Category> findById(String id);
    Optional<Category> findCategoryBySlug(String slug);
    Category createCategory(CategoryForm categoryForm);
    Category updateCategory(String id, CategoryForm categoryForm);

    void setActivateCategory(String categoryId, boolean isActive);
    void deleteCategory(String categoryId);

}
