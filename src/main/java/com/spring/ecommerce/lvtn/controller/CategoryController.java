package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.CategoryForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import com.spring.ecommerce.lvtn.service.CategoryService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/category")

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategory() {
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        categoryService.findAll()
                )
        );
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Optional<Category>>> getCategoryById(@PathVariable String categoryId){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        categoryService.findById(categoryId)
                )
        );
    }
    @GetMapping("slug/{slug}")
    public ResponseEntity<ApiResponse<Optional<Category>>> getCategoryBySlug(@PathVariable String slug){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        categoryService.findCategoryBySlug(slug)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody CategoryForm categoryForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.CREATED,
                        categoryService.createCategory(categoryForm)
                )
        );
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable String categoryId, @RequestBody CategoryForm categoryForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPDATED,
                        categoryService.updateCategory(categoryId, categoryForm)
                )
        );
    }

    @PutMapping("/{categoryId}/activate")
    public ResponseEntity<ApiResponse<String>> setActivateCategory(@PathVariable String categoryId, @RequestParam boolean isActive){
        categoryService.setActivateCategory(categoryId, isActive);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPDATED,
                        "Updated"
                )
        );
    }

}
