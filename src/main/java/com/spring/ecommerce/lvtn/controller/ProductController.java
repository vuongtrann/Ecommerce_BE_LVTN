package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.ProductForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import com.spring.ecommerce.lvtn.service.ProductService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/product")

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProduct(){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        productService.findAll()
                )
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Optional<Product>>> getProductById(@PathVariable String productId){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        productService.findById(productId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody ProductForm productForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.CREATED,
                        productService.createProduct(productForm)
                )
        );
    }
}
