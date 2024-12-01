package com.spring.ecommerce.lvtn.model.Entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brands")
@Data
public class Brand extends BaseEntity{
    private Long brandId;
    private String name;
    private String slug;
    private String description;
}
