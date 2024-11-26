package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.CollectionForm;
import com.spring.ecommerce.lvtn.model.Entity.Collection;

import java.util.List;
import java.util.Optional;

public interface CollectionService {
    List<Collection> findAll();
    Optional<Collection> findById(String id);
    Optional<Collection> findBySlug(String slug);
    Collection createCollection(CollectionForm form);
    Collection updateCollection(String id, CollectionForm form);
    void setActivate(String id, boolean isActive);
    void deleteCollection(String id);
}
