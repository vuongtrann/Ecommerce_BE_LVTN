package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.exception.AppException;
import com.spring.ecommerce.lvtn.model.Dao.Request.CollectionForm;
import com.spring.ecommerce.lvtn.model.Entity.Collection;
import com.spring.ecommerce.lvtn.repository.CollectionRepository;
import com.spring.ecommerce.lvtn.service.CollectionService;
import com.spring.ecommerce.lvtn.service.Util.SlugifyService;
import com.spring.ecommerce.lvtn.utils.enums.ErrorCode;
import com.spring.ecommerce.lvtn.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionServiceIMPL implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final SlugifyService slugifyService;

    @Override
    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    @Override
    public Optional<Collection> findById(String id) {
        return collectionRepository.findById(id);
    }

    @Override
    public Optional<Collection> findBySlug(String slug) {
        return collectionRepository.findByCollectionSlug(slug);
    }

    @Override
    public Collection createCollection(CollectionForm form) {
        boolean exists = collectionRepository.existsByCollectionName(form.getCollectionName());
        if (exists) {
            throw new RuntimeException("Collection already exists");
        }
        Collection collection = new Collection();
        collection.setCollectionName(form.getCollectionName());
        collection.setCollectionDescription(form.getCollectionDescription());
        collection.setCollectionSlug(slugifyService.generateSlug(form.getCollectionName()));
        return collectionRepository.save(collection);
    }

    @Override
    public Collection updateCollection(String id, CollectionForm form) {
        Optional<Collection> collection = collectionRepository.findById(id);
        boolean exists = collectionRepository.existsByCollectionName(form.getCollectionName());
       if (collection.isEmpty()) {
           throw new RuntimeException("Collection not found");
       }
       else if (exists)
       {
           throw new RuntimeException("Collection already exists");
       }
       else
       {
           Collection collection1 = collection.get();
           collection1.setCollectionName(form.getCollectionName());
           collection1.setCollectionDescription(form.getCollectionDescription());
           collection1.setCollectionSlug(slugifyService.generateSlug(form.getCollectionName()));
           return collectionRepository.save(collection1);
       }

    }

    @Override
    public void setActivate(String id, boolean isActive) {
       Collection collection = collectionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
       collection.setStatus(isActive? Status.ACTIVE : Status.INACTIVE);
       collectionRepository.save(collection);
    }

    @Override
    public void deleteCollection(String id) {
//        Collection collection = collectionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        collectionRepository.deleteById(id);
    }
}
