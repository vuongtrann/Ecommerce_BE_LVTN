package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.VariantOption;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VariantOptionRepository extends MongoRepository<VariantOption, String> {
}
