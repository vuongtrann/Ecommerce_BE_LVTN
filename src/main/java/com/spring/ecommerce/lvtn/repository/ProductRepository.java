package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findProductBySlug(String slug);
    Page<ProductProjection> findAllProjectedBy(Pageable pageable);
    Page<ProductProjection> findAllProjectedByCategories(String categoryId, Pageable pageable);

    Page<ProductProjection> findAllProjectedByNameContaining(String keyword, Pageable pageable);

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    Page<ProductProjection> findAllProjectedByName(String keyword, Pageable pageable);


    // Lấy sản phẩm cùng category, sử dụng projection và phân trang
    @Query("{ 'categories': ?0, '_id': { $ne: ?1 } }")
    Page<ProductProjection> findByCategoryWithProjection(String category, String excludedId, Pageable pageable);

    // Lấy sản phẩm trong khoảng giá
    @Query("{ 'sellingPrice': { $gte: ?0, $lte: ?1 }, '_id': { $ne: ?2 } }")
    Page<ProductProjection> findByPriceRangeWithProjection(double minPrice, double maxPrice, String excludedId, Pageable pageable);

    List<Product> findAllByIdIn(List<String> productIds);
}
