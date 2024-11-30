package com.spring.ecommerce.lvtn.model.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "recently_viewed_product")
@Data
public class RecentlyViewedProduct {

    @Field(name = "userId")
    private Long userId;
    @Field(name = "productId")
    private String productId;
    @Field(name = "viewedAt")
    private Instant viewedAt;
}
