package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.ProductForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.ProductRequest;
import com.spring.ecommerce.lvtn.model.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Product createProduct(ProductForm productForm);
    Product updateProduct(String id, ProductForm productForm);
    Optional<Product> findById(String id);
    List<Product> findAll();
    List<Product> findByCategory(String categoryId);
    List<Product> findByBrand(String brandId);


}
