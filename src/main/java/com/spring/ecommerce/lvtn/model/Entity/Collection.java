package com.spring.ecommerce.lvtn.model.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("Collections")
public class Collection extends BaseEntity {

    private String collectionName;
    private String collectionDescription;
    private String collectionImage;
    private String collectionSlug;
}
