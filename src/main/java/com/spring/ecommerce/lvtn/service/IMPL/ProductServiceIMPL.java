package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.model.Dao.Request.ProductForm;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import com.spring.ecommerce.lvtn.repository.ProductRepository;
import com.spring.ecommerce.lvtn.service.CategoryService;
import com.spring.ecommerce.lvtn.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ProductServiceIMPL implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductForm productForm) {
        List<Category> categories = productForm.getCategories().stream()
                .map(category ->categoryService.findById(category).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        Product product = new Product(
                productForm.getName(),
                productForm.getDescription(),
                productForm.getSku(),
                productForm.getQuantityAvailable(),
                productForm.getOriginalPrice(),
                productForm.getSellingType(),
                categories
        );

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(String id, ProductForm productForm) {
        return null;
    }

    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
    }

    @Override
    public List<Product> findByCategory(String categoryId) {
        return List.of();
    }

    @Override
    public List<Product> findByBrand(String brandId) {
        return List.of();
    }
}
