package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.exception.AppException;
import com.spring.ecommerce.lvtn.model.Dao.Request.CategoryForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.CategoryProjection;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.VariantOption;
import com.spring.ecommerce.lvtn.repository.CategoryRepository;
import com.spring.ecommerce.lvtn.service.CategoryService;
import com.spring.ecommerce.lvtn.service.Util.SlugifyService;
import com.spring.ecommerce.lvtn.utils.enums.ErrorCode;
import com.spring.ecommerce.lvtn.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceIMPL implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final SlugifyService slugifyService;


    @Override
    public Page<CategoryProjection> findAll(int page, int size, String sortBy, String direction){
       Sort sort = Sort.by(Sort.Order.by(sortBy));
       if (direction.equalsIgnoreCase("desc")) {
           sort = sort.descending();
       } else if (direction.equalsIgnoreCase("asc")) {
           sort = sort.ascending();
       }
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryRepository.findAllProjectedBy(pageable);
    }

    @Override
    public Optional<Category> findById(String categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryBySlug(String slug) {
        return categoryRepository.findCategoriesBySlug(slug);
    }

    @Override
    public Category createCategory(CategoryForm categoryForm) {
        boolean existedCategory = categoryRepository.existsByName(categoryForm.getName());
        if (existedCategory) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        List<Category> parentCategories = categoryForm.getParentId().stream()
                .map(parent -> categoryRepository.findById(parent).orElse(null))
                .filter(Objects::nonNull)
                .toList();


        List<Category> childCategories = categoryForm.getChildId().stream()
                .map(child -> categoryRepository.findById(child).orElse(null))
                .filter(Objects::nonNull)
                .toList();


        Category category = new Category(categoryForm.getName(),childCategories, parentCategories);
        category.setSlug(slugifyService.generateSlug(categoryForm.getName()));
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(Instant.now());
        category.setVariantTypes(categoryForm.getVariantTypes());
        category.setSpecificationTypes(categoryForm.getSpecificationTypes());
        category.setIsFeatured(categoryForm.getIsFeatured());

        category = categoryRepository.save(category);

        // Set this category as a child for each parent
        for (Category parent : parentCategories) {
            parent.getChildren().add(category);
            categoryRepository.save(parent);
        }

        // Set this category as a parent for each child
        for (Category child : childCategories) {
            child.getParent().add(category);
            categoryRepository.save(child);
        }


        return category;
    }

    @Override
    public Category updateCategory(String id, CategoryForm categoryForm) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        boolean existedCategory = categoryRepository.existsByName(categoryForm.getName());
        if (existedCategory && !category.getName().equals(categoryForm.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        List<Category> newParentCategories = categoryForm.getParentId().stream()
                .map(parent -> categoryRepository.findById(parent).orElse(null))
                .filter(Objects::nonNull)
                .toList();
        List<Category> newChildCategories = categoryForm.getChildId().stream()
                .map(child -> categoryRepository.findById(child).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        // Update parent relationships if there are changes
        if (!category.getParent().equals(newParentCategories)) {
            category.getParent().forEach(parent -> parent.getChildren().remove(category));
            newParentCategories.forEach(parent -> parent.getChildren().add(category));
            category.setParent(newParentCategories);
        }

        // Update child relationships if there are changes
        if (!category.getChildren().equals(newChildCategories)) {
            category.getChildren().forEach(child -> child.getParent().remove(category));
            newChildCategories.forEach(child -> child.getParent().add(category));
            category.setChildren(newChildCategories);
        }

        category.setName(categoryForm.getName());
        category.setSlug(slugifyService.generateSlug(categoryForm.getName()));
        category.setUpdatedAt(Instant.now());
        category.setVariantTypes(categoryForm.getVariantTypes());
        category.setSpecificationTypes(categoryForm.getSpecificationTypes());
        category.setIsFeatured(categoryForm.getIsFeatured());

        return categoryRepository.save(category);
    }

    @Override
    public void setActivateCategory(String categoryId, boolean isActive) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setStatus(isActive ? Status.ACTIVE : Status.INACTIVE);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (!category.getParent().isEmpty() || !category.getChildren().isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_HAS_RELATIONSHIPS);
        }

        categoryRepository.deleteById(categoryId);
    }

}
