package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.BrandForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.BrandProjection;
import com.spring.ecommerce.lvtn.model.Entity.Brand;
import org.springframework.data.domain.Page;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BrandService {
    Page<BrandProjection> findAll(int page, int size, String sortBy, String direction);
    Optional<Brand> findById(String id);
    Optional<Brand> findByBrandId(Long brandId);
    Optional<Brand> findBySlug(String slug);
    Brand createBrand(BrandForm brandForm);
    Brand updateBrand(Long brandId, BrandForm brandForm);
    void hardDelete(Long brandId);
    void softDelete(Long brandId);
    void setActivateBrand(Long brandId, boolean isActive);
}
