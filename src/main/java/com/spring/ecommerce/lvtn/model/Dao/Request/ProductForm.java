package com.spring.ecommerce.lvtn.model.Dao.Request;


import com.spring.ecommerce.lvtn.model.Entity.ProductVariant.ProductVariant;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ProductForm {


    private String name;

    private String description;

    private String slug;

    private String primaryImageURL;

    private List<String> imagesURL = new ArrayList<>();

    private String sku;


    private int quantityAvailable;

    private int soldQuantity;

    private double originalPrice; //gia goc

    private double sellingPrice; //gia ban

    private double discountedPrice; //gia giam

    private int noOfView;

    private String sellingType;

    private List<String> categories = new ArrayList<>();

    private List<String> brands = new ArrayList<>();

    private boolean hasVariants;
    private List<ProductVariant> variants = new ArrayList<>();
    private Map<String, List<String>> options;

    private List<String> collections;

    private Map <String, String> specifications;
}
