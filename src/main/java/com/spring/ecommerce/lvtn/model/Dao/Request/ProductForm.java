package com.spring.ecommerce.lvtn.model.Dao.Request;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    private BigDecimal originalPrice; //gia goc

    private BigDecimal sellingPrice; //gia ban

    private BigDecimal discountedPrice; //gia giam

    private int noOfView;

    private String sellingType;

    private List<String> categories = new ArrayList<>();
}
