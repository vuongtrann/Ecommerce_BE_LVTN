package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.ProductForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import com.spring.ecommerce.lvtn.service.ProductService;
import com.spring.ecommerce.lvtn.service.RecentlyViewedService;
import com.spring.ecommerce.lvtn.service.UploadFileService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/product")

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {
    private final ProductService productService;
    private final UploadFileService uploadFileService;
    private final RecentlyViewedService recentlyViewedService;

    @GetMapping
    public ResponseEntity<Page<ProductProjection>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(productService.findAll(page, size, sortBy, direction));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Optional<Product>>> getProductById(@PathVariable String productId, @RequestHeader("userId") Long userId){
        recentlyViewedService.addProductToRecentlyViewed(userId, productId);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        productService.findById(productId)
                )
        );
    }
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<Optional<Product>>> getProductBySlug(@PathVariable String slug, @RequestHeader("userId") Long userId){
        Optional<Product> product = productService.findBySlug(slug);
        product.ifPresent(value -> recentlyViewedService.addProductToRecentlyViewed(userId, value.getId()));
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        product
                )
        );
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductProjection>> getProductByCategory(
            @PathVariable String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(productService.findByCategory(categoryId, page, size, sortBy, direction));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductProjection>> searchProduct(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(productService.searchProduct(keyword, page, size, sortBy, direction));
    }

    @GetMapping("/slug/{slug}/similar")
    public ResponseEntity<Page<ProductProjection>> getSimilarProduct(@PathVariable String slug, Pageable pageable){
        return ResponseEntity.ok(productService.similarProduct(slug, pageable));
    }

    @GetMapping("/recently-viewed")
    public ResponseEntity<ApiResponse<List<Product>>> getRecentlyViewedProducts(@RequestHeader("userId") Long userId){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        recentlyViewedService.getAllRecentlyViewedProducts(userId)
                )
        );
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductForm productForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.CREATED,
                        productService.createProduct(productForm)
                )
        );
    }
    @PostMapping("/{productId}/image/upload")
    public ResponseEntity<ApiResponse<Product>> uploadImage(@PathVariable String productId ,@RequestParam("file") List<MultipartFile> files) throws InterruptedException, IOException {
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPLOADED,
                        productService.uploadProductImage(productId, files)
                )
        );
    }
    @DeleteMapping("/{productId}/image/delete")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable String productId, @RequestBody String imageUrl){
        productService.delteteImage(productId, imageUrl);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.DELETED,
                        "Deleted image successfully !"
                )
        );
    }




}
