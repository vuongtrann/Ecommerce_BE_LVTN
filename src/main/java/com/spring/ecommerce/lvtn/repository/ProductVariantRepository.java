package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.ProductVariant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductVariantRepository extends MongoRepository<ProductVariant, String> {
}
