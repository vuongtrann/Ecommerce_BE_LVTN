package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.exception.AppException;
import com.spring.ecommerce.lvtn.model.Dao.Request.ProductForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ProductProjection;
import com.spring.ecommerce.lvtn.model.Entity.Category;
import com.spring.ecommerce.lvtn.model.Entity.Collection;
import com.spring.ecommerce.lvtn.model.Entity.Product;
import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.ProductVariant;
import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.VariantOption;
import com.spring.ecommerce.lvtn.repository.ProductRepository;
import com.spring.ecommerce.lvtn.repository.ProductVariantRepository;
import com.spring.ecommerce.lvtn.repository.VariantOptionRepository;
import com.spring.ecommerce.lvtn.service.CategoryService;
import com.spring.ecommerce.lvtn.service.CollectionService;
import com.spring.ecommerce.lvtn.service.ProductService;
import com.spring.ecommerce.lvtn.service.UploadFileService;
import com.spring.ecommerce.lvtn.service.Util.SlugifyService;
import com.spring.ecommerce.lvtn.utils.enums.ErrorCode;
import com.spring.ecommerce.lvtn.utils.enums.VariantType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ProductServiceIMPL implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CollectionService collectionService;

    private final VariantOptionRepository variantOptionRepository;
    private final ProductVariantRepository  productVariantRepository;

    private final SlugifyService slugifyService;
    private final UploadFileService uploadFileService;


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductForm productForm) {
        List<Category> categories = productForm.getCategories().stream()
                .map(category ->categoryService.findById(category).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Collection> collections = productForm.getCollections().stream()
                .map(collection -> collectionService.findById(collection).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        Product product = new Product(
                productForm.getName(),
                productForm.getDescription(),
                slugifyService.generateSlug(productForm.getName()),
                productForm.getSku(),
                productForm.getQuantityAvailable(),
                productForm.getOriginalPrice(),
                productForm.getSellingType(),
                categories,
                collections
        );

        //variant
        if(productForm.isHasVariants() && productForm.getVariants() != null){
//            productForm.setOptions(productForm.getOptions());
            List<ProductVariant> newVariants = new ArrayList<>();;

            List<VariantType> availableVariantTypes = new ArrayList<>();
            for (Category category : categories) {
                availableVariantTypes.addAll(category.getVariantTypes());
            }

            //taoj ra cac variant tu form
            for (ProductVariant variantForm : productForm.getVariants()) {
                List<VariantOption> variantOptionList = new ArrayList<>();
                //tao ra cac variant option dua tren cac option trong form
                for (VariantOption option : variantForm.getVariantOptions()){
                    try{
                        // chuyen doi laoi tu chuoi sang variant type enum
                        VariantType variantType = VariantType.valueOf(option.getType().toString().toUpperCase());
                        if (availableVariantTypes.contains(variantType)){
                            VariantOption variantOption = new VariantOption(variantType, option.getValue());
                            variantOption = variantOptionRepository.save(variantOption);
                            variantOptionList.add(variantOption);
                        }
                    }catch (IllegalArgumentException e){
                        //neu khong tim thay enum thi bo qua
                        System.out.println("Invalid variant type: " + option.getType());
                    }
                }


                ProductVariant newVariant = new ProductVariant();
                newVariant.setVariantOptions(variantOptionList);
                newVariant.setQuantityAvailable(variantForm.getQuantityAvailable());
                newVariant.setSoldQuantity(variantForm.getSoldQuantity());
                newVariant.setSellingPrice(variantForm.getSellingPrice());
                newVariant.setOriginalPrice(variantForm.getOriginalPrice());
                newVariant.setDiscountedPrice(variantForm.getDiscountedPrice());
                newVariant.setImageURLs(variantForm.getImageURLs());
                newVariants.add(newVariant);
            }

            //save tat ca product variant moi chi 1 lan
            productVariantRepository.saveAll(newVariants);
            product.setHasVariants(true);
            product.setVariants(newVariants);
        }else {
            product.setHasVariants(false);
            product.setVariants(null);
        }


        product.setSpecifications(productForm.getSpecifications());
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(String id, ProductForm productForm) {
        return null;
    }

    @Override
    public Product uploadProductImage(String id, List<MultipartFile> imageUrls) throws InterruptedException, IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<String> imageURLs = uploadFileService.uploadFiles(imageUrls, product.getId(),product.getSlug());
        product.setPrimaryImageURL(imageURLs.get(0));
        product.setImagesURL(imageURLs);
        Product savedProduct = save(product);
        return savedProduct;
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setNoOfView(product.getNoOfView() + 1);
        save(product);
        return Optional.of(product);
    }

    @Override
    public Page<ProductProjection> findAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }else if (direction.equalsIgnoreCase("asc")){
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllProjectedBy(pageable);
    }

    @Override
    public Page<ProductProjection> findByCategory(String categoryId, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }else if (direction.equalsIgnoreCase("asc")){
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllProjectedByCategories(categoryId, pageable);
    }

    @Override
    public Page<ProductProjection> searchProduct(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }else if (direction.equalsIgnoreCase("asc")){
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllProjectedByName(keyword, pageable);
    }

    @Override
    public List<Product> findByBrand(String brandId) {
        return List.of();
    }

    @Override
    public Optional<Product> findBySlug(String slug) {
        Product product = productRepository.findProductBySlug(slug).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setNoOfView(product.getNoOfView() + 1);
        save(product);
        return Optional.of(product);
    }

    @Override
    public void delteteImage(String id, String imageUrl) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<String> imageUrls = product.getImagesURL();
        if (!imageUrls.contains(imageUrl)){
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }else if (imageUrls.size() == 1){
            throw new AppException(ErrorCode.CANNOT_DELETE_PRIMARY_IMAGE);
        }else {
            uploadFileService.deleteFileByUrl(imageUrl);
            imageUrls.remove(imageUrl);
            product.setImagesURL(imageUrls);
        }
    }

}
