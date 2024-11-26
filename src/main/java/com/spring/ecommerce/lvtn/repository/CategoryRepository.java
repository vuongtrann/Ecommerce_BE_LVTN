package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Dao.Respone.CategoryProjection;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Page<CategoryProjection> findAllProjectedBy(Pageable pageable);
    Optional<Category> findCategoriesBySlug(String slug);
    boolean existsByName(String name);

}
