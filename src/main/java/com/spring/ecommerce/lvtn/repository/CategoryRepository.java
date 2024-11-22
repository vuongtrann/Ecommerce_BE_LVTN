package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Entity.Category;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findCategoriesBySlug(String slug);

    boolean existsByName(String name);

}
