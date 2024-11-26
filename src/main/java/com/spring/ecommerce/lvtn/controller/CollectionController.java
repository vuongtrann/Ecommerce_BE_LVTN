package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.CollectionForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Entity.Collection;
import com.spring.ecommerce.lvtn.service.CollectionService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Collection>>> getAllCollection(){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        collectionService.findAll()
                )
        );
    }
    @GetMapping("/{collectionId}")
    public ResponseEntity<ApiResponse<Optional<Collection>>> getCollectionById(@PathVariable String collectionId){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        collectionService.findById(collectionId)
                )
        );
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<Optional<Collection>>> getCollectionBySlug(@PathVariable String slug){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        collectionService.findBySlug(slug)
                )
        );
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Collection>> addCollection(@RequestBody CollectionForm collectionForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.CREATED,
                        collectionService.createCollection(collectionForm)
                )
        );
    }
    @PutMapping("/{collectionId}")
    public ResponseEntity<ApiResponse<Collection>> updateCollection(@PathVariable String collectionId, @RequestBody CollectionForm collectionForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPDATED,
                        collectionService.updateCollection(collectionId, collectionForm)
                )
        );
    }
    @DeleteMapping("/{collectionId}")
    public ResponseEntity<ApiResponse<String>> deleteCollection(@PathVariable String collectionId){
        collectionService.deleteCollection(collectionId);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.DELETED,
                        "Deleted"
                )
        );
    }
    @PutMapping("/{collectionId}/activate")
    public ResponseEntity<ApiResponse<String>> setActivate(@PathVariable String collectionId, @RequestParam boolean isActive){
        collectionService.setActivate(collectionId, isActive);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPDATED,
                        "Updated status successfully !"
                )
        );
    }



}
