package com.spring.ecommerce.lvtn.model.Dao.Respone;

import com.spring.ecommerce.lvtn.utils.enums.Status;

import java.time.Instant;

public interface CategoryProjection {
    String getId();
    String getName();
    String getSlug();
    String getBanner();
    int getLevel();
    Status getStatus();
    Boolean getIsFeatured();
    Instant getCreatedAt();
    Instant getUpdatedAt();

}
