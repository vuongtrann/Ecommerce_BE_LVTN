package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Entity.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CollectionRepository extends MongoRepository<Collection, String> {
    Optional<Collection> findByCollectionSlug(String slug);
    boolean existsByCollectionName(String name);

}
