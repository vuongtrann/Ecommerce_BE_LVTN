package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecentlyViewedService {
    public void addProductToRecentlyViewed(Long userId, String productId);
    public List<String> getRecentlyViewedProducts(Long userId);
    public List<Product> getAllRecentlyViewedProducts(Long userId);
}
