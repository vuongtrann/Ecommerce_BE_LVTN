package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Entity.RecentlyViewedProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentlyViewedProductRepository extends MongoRepository<RecentlyViewedProduct,String> {
    List<RecentlyViewedProduct> findByUserId(Long userId);

    long countByUserIdAndProductId(Long userId, String productId);

    void deleteByUserIdAndProductId(Long userId, String productId);

    List<RecentlyViewedProduct> findByUserIdOrderByViewedAtDesc(Long userId); // Sắp xếp giảm dần theo thời gian xem
}
