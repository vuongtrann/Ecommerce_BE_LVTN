package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.exception.AppException;
import com.spring.ecommerce.lvtn.model.Dao.Request.BrandForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.BrandProjection;
import com.spring.ecommerce.lvtn.model.Entity.Brand;
import com.spring.ecommerce.lvtn.repository.BrandRepository;
import com.spring.ecommerce.lvtn.service.BrandService;
import com.spring.ecommerce.lvtn.service.Util.SequenceGeneratorService;
import com.spring.ecommerce.lvtn.service.Util.SlugifyService;
import com.spring.ecommerce.lvtn.utils.enums.ErrorCode;
import com.spring.ecommerce.lvtn.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BrandServiceIMPL implements BrandService {
    private final BrandRepository brandRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final SlugifyService slugifyService;

    @Override
    public Page<BrandProjection> findAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        } else if (direction.equalsIgnoreCase("asc")) {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return brandRepository.findAllProjectedBy(pageable);
    }

    @Override
    public Optional<Brand> findById(String id) {
        return brandRepository.findById(id);
    }

    @Override
    public Optional<Brand> findByBrandId(Long brandId) {
        return brandRepository.findByBrandId(brandId);
    }

    @Override
    public Optional<Brand> findBySlug(String slug) {
        return brandRepository.findBrandBySlug(slug);
    }

    @Override
    public Brand createBrand(BrandForm brandForm) {
        boolean existBrandName = brandRepository.existsByName(brandForm.getName());
        if (existBrandName) {
            throw new AppException(ErrorCode.BRAND_ALREADY_EXISTS);
        }
        Brand brand = new Brand();
        brand.setBrandId(sequenceGeneratorService.generateSequence("brand_sequence"));
        brand.setName(brandForm.getName());
        brand.setSlug(slugifyService.generateSlug(brandForm.getName()));
        brand.setDescription(brandForm.getDescription());
        brand.setCreatedAt(Instant.now());
        brand.setUpdatedAt(Instant.now());
        brand.setStatus(Status.ACTIVE);

        Brand savedBrand = brandRepository.save(brand);
        return savedBrand;
    }

    @Override
    public Brand updateBrand(Long brandId, BrandForm brandForm) {
        Brand brand = findByBrandId(brandId).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brand.setName(brandForm.getName());
        brand.setSlug(slugifyService.generateSlug(brandForm.getName()));
        brand.setDescription(brandForm.getDescription());
        brand.setUpdatedAt(Instant.now());

        Brand savedBrand = brandRepository.save(brand);
        return savedBrand;

    }

    @Override
    public void hardDelete(Long brandId) {
        Brand brand = findByBrandId(brandId).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brandRepository.delete(brand);
    }

    @Override
    public void softDelete(Long brandId) {
        Brand brand = findByBrandId(brandId).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brand.setStatus(Status.DELETED);
        brandRepository.save(brand);
    }

    @Override
    public void setActivateBrand(Long brandId, boolean isActive) {
        Brand brand = findByBrandId(brandId).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brand.setStatus(isActive ? Status.ACTIVE : Status.INACTIVE);
        brandRepository.save(brand);

    }


}
