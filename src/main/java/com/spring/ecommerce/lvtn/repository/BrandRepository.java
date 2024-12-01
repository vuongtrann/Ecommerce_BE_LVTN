package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Dao.Respone.BrandProjection;
import com.spring.ecommerce.lvtn.model.Entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BrandRepository extends MongoRepository<Brand,String> {
    @Query("{ 'status': 'ACTIVE' }")
    Page<BrandProjection> findAllProjectedBy(Pageable pageable);

    @Query("{ 'brandId' : ?0 ,'status': 'ACTIVE'}")
    Optional<Brand> findByBrandId(Long brandId);

    @Query("{ 'slug': ?0, 'status': 'ACTIVE' }")
    Optional<Brand> findBrandBySlug(String slug);

    boolean existsByName(String name);
}
