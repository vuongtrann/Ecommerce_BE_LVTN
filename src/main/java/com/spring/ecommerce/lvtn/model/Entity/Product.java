package com.spring.ecommerce.lvtn.model.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.ProductVariant;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseEntity{

    @Id
    private String id;
    private String name;
    private String description;
    private String slug;
    private String primaryImageURL;
    private List<String> imagesURL;
    private String sku;
    private int quantityAvailable;
    private int soldQuantity;
    private BigDecimal originalPrice; //gia goc
    private BigDecimal sellingPrice; //gia ban
    private BigDecimal discountedPrice; //gia giam
    private int noOfView;
    private String sellingType;

    @DBRef(lazy = true)
    private List<Category> categories;
    @DBRef(lazy = true)
    private List<Collection> collections;

    private Boolean hasVariants;
    @DBRef(lazy = true)
    private List<ProductVariant> variants ;
    private Map<String, List<String>> options;
    private Map <String, String> specifications;



    public Product(String name, String description, String slug, String sku, int quantityAvailable, BigDecimal originalPrice, String sellingType, List<Category> categories, List<Collection> collections) {
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.sku = sku;
        this.quantityAvailable = quantityAvailable;
        this.originalPrice = originalPrice;
        this.sellingType = sellingType;
        this.categories = categories;
        this.collections = collections;
    }
}
