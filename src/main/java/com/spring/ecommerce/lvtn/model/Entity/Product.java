package com.spring.ecommerce.lvtn.model.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document("Products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseEntity{

    @Id
    private String ID;
    private String name;
    private String description;
    private String slug;
    private String primaryImageURL;
    private List<String> imagesURL = new ArrayList<>();
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

    public Product(String name, String description, String sku, int quantityAvailable, BigDecimal originalPrice, String sellingType, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.quantityAvailable = quantityAvailable;
        this.originalPrice = originalPrice;
        this.sellingType = sellingType;
        this.categories = categories;
    }
}
