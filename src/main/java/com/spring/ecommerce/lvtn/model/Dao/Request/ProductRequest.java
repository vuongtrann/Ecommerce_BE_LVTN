package com.spring.ecommerce.lvtn.model.Dao.Request;

import jakarta.validation.constraints.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 150, message = "Name must be between 5 and 150 characters")
    String name;

    String description;

    String slug;

    String primaryImageURL;

    List<String> imagesURL = new ArrayList<>();

    String sku;

    @NotNull(message = "Quantity Available is required")
    @Min(value = 1, message = "Quantity Available must be greater than 1")
    @Max(value = 9999, message = "Quantity Available must be less than 9999")
    int quantityAvailable;

    int soldQuantity;
    @NumberFormat
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "The price must be a valid number with up 2 decimal places")
    BigDecimal originalPrice; //gia goc

    @NumberFormat
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "The price must be a valid number with up 2 decimal places")
    BigDecimal sellingPrice; //gia ban

    @NumberFormat
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "The price must be a valid number with up 2 decimal places")
    BigDecimal discountedPrice; //gia giam

    int noOfView;
    String sellingType;
}
