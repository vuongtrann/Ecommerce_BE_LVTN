package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findProductBySlug(String slug);
    Page<ProductProjection> findAllProjectedBy(Pageable pageable);
    Page<ProductProjection> findAllProjectedByCategories(String categoryId, Pageable pageable);

    Page<ProductProjection> findAllProjectedByNameContaining(String keyword, Pageable pageable);

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    Page<ProductProjection> findAllProjectedByName(String keyword, Pageable pageable);
}
