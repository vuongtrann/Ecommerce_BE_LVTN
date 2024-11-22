package com.spring.ecommerce.lvtn.model.Entity.ProductSpetification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class ProductSpecification {
    private String description;
    private List<SpecificationOption> specificationOptions;
}
