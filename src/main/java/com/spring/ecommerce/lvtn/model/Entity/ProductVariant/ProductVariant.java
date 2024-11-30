package com.spring.ecommerce.lvtn.model.Entity.ProductVariant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.ecommerce.lvtn.model.Entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("ProductVariants")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariant extends BaseEntity {

    private String sku;
    private int quantityAvailable;
    private long soldQuantity;
    private BigDecimal sellingPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private List<String> imageURLs;
    private List<VariantOption> variantOptions;
}
