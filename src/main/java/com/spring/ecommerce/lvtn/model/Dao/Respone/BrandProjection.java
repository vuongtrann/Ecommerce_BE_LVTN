package com.spring.ecommerce.lvtn.model.Dao.Respone;

import com.spring.ecommerce.lvtn.utils.enums.Status;

public interface BrandProjection {
    String getId();
    Long getBrandId();
    String getName();
    String getSlug();
    String getDescription();
    Status getStatus();

}
