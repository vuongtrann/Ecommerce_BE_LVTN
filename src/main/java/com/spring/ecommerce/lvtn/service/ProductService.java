package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.ProductForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Product createProduct(ProductForm productForm);
    Product updateProduct(String id, ProductForm productForm);
    Product uploadProductImage(String id, List<MultipartFile> imageUrls) throws InterruptedException, IOException;
    Optional<Product> findById(String id);
    Page<ProductProjection> findAll(int page, int size, String sortBy, String direction);
    Page<ProductProjection> findByCategory(String categoryId, int page, int size, String sortBy, String direction);
    Page<ProductProjection> searchProduct(String keyword, int page, int size, String sortBy, String direction);
    List<Product> findByBrand(String brandId);
    Optional<Product> findBySlug(String slug);
    Page<ProductProjection> similarProduct(String productSlug, Pageable pageable);
    void delteteImage(String id, String imageUrl);


}
