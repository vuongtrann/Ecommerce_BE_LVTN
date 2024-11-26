package com.spring.ecommerce.lvtn.model.Dao.Request;

import com.spring.ecommerce.lvtn.utils.enums.VariantType;
import lombok.Data;

import java.util.List;

@Data
public class CategoryForm {
    private String name;
    private String slug;
    private String icon;
    private String banner;
    private List<String> parentId;
    private List<String> childId;
    private int level;
    private List<VariantType> variantTypes;
    private List<VariantType> specificationTypes;

    private Boolean isFeatured;

}
