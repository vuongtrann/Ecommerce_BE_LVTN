package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.exception.AppException;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import com.spring.ecommerce.lvtn.model.Entity.RecentlyViewedProduct;
import com.spring.ecommerce.lvtn.model.Entity.User;
import com.spring.ecommerce.lvtn.repository.RecentlyViewedProductRepository;
import com.spring.ecommerce.lvtn.repository.UserRepository;
import com.spring.ecommerce.lvtn.service.ProductService;
import com.spring.ecommerce.lvtn.service.RecentlyViewedService;
import com.spring.ecommerce.lvtn.utils.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecentlyViewedServiceIMPL implements RecentlyViewedService {
    private final ProductService productService;
    private final RecentlyViewedProductRepository recentlyViewedProductRepository;

    @Transactional
    public void addProductToRecentlyViewed(Long userId, String productId) {
        long count = recentlyViewedProductRepository.countByUserIdAndProductId(userId, productId);
        if (count > 0) {
           return;
        }
        RecentlyViewedProduct recentlyViewedProduct = new RecentlyViewedProduct();
        recentlyViewedProduct.setUserId(userId);
        recentlyViewedProduct.setProductId(productId);
        recentlyViewedProduct.setViewedAt(Instant.now());

        recentlyViewedProductRepository.save(recentlyViewedProduct);

        // Giới hạn số lượng sản phẩm đã xem (10 sản phẩm gần nhất)
        List<RecentlyViewedProduct> allViewed = recentlyViewedProductRepository.findByUserId(userId);
        if (allViewed.size() > 20) {
            // Xóa sản phẩm cũ nhất nếu vượt quá giới hạn
            RecentlyViewedProduct oldest = allViewed.stream()
                    .min(Comparator.comparing(RecentlyViewedProduct::getViewedAt))
                    .orElseThrow(() -> new RuntimeException("No product found"));
            recentlyViewedProductRepository.deleteByUserIdAndProductId(userId, oldest.getProductId());
        }
    }

    @Override
    public List<String> getRecentlyViewedProducts(Long userId) {
        // Lấy danh sách sản phẩm đã xem
        List<RecentlyViewedProduct> recentlyViewed = recentlyViewedProductRepository.findByUserId(userId);
        return recentlyViewed.stream()
                .map(RecentlyViewedProduct::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllRecentlyViewedProducts(Long userId) {
        List<RecentlyViewedProduct> recentlyViewed = recentlyViewedProductRepository.findByUserIdOrderByViewedAtDesc(userId);
        List<String> productIds = recentlyViewed.stream()
                .map(RecentlyViewedProduct::getProductId)
                .collect(Collectors.toList());

        List<Product> products = productService.findAllByIds(productIds);
        return products;
    }

}
