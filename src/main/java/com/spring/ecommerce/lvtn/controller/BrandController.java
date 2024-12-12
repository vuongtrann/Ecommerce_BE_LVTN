package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.BrandForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Dao.Respone.BrandProjection;
import com.spring.ecommerce.lvtn.model.Entity.Brand;
import com.spring.ecommerce.lvtn.service.BrandService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<Page<BrandProjection>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(brandService.findAll(page, size, sortBy, direction));
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<ApiResponse<Optional<Brand>>> getBrandById(@PathVariable Long brandId){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        brandService.findByBrandId(brandId)
                )
        );
    }
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<Optional<Brand>>> getBrandBySlug(@PathVariable String slug){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.FETCHED,
                        brandService.findBySlug(slug)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Brand>> addBrand(@RequestBody BrandForm brandForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.CREATED,
                        brandService.createBrand(brandForm)
                )
        );
    }
    @PutMapping("/{brandId}")
    public ResponseEntity<ApiResponse<Brand>> updateBrand(@PathVariable Long brandId, @RequestBody BrandForm brandForm){
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPDATED,
                        brandService.updateBrand(brandId, brandForm)
                )
        );
    }

    @PutMapping("/{brandId}/activate")
    public ResponseEntity<ApiResponse<String>> setActiveBrand(@PathVariable Long brandId, @RequestParam boolean isActive){
        brandService.setActivateBrand(brandId, isActive);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.UPDATED,
                        "Updated status successfully !"
                )
        );
    }
    @DeleteMapping("/{brandId}")
    public ResponseEntity<ApiResponse<String>> deleteBrand(@PathVariable Long brandId){
        brandService.softDelete(brandId);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.DELETED,
                        "Deleted successfully !"
                )
        );
    }

    @DeleteMapping("/{brandId}/hard")
    public ResponseEntity<ApiResponse<String>> hardDeleteBrand(@PathVariable Long brandId){
        brandService.hardDelete(brandId);
        return ResponseEntity.ok(
                ApiResponse.builderResponse(
                        SuccessCode.DELETED,
                        "Deleted form DB !"
                )
        );
    }

}
