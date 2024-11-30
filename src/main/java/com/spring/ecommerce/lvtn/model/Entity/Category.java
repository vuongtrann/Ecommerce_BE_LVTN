package com.spring.ecommerce.lvtn.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.ecommerce.lvtn.utils.enums.VariantType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("Categories")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends BaseEntity{

    private String name;
    private String slug;
    private String icon;
    private String banner;
    private int level;

    @DBRef(lazy = true)
    @JsonIgnoreProperties({ "parent", "children"})
    private List<Category> parent = new ArrayList<>();

    @DBRef(lazy = true)
    @JsonIgnoreProperties({"parent", "children"})
    private List<Category> children = new ArrayList<>();

    private List<VariantType> variantTypes;
    private List<VariantType> specificationTypes;
    private Boolean isFeatured;



    public Category(String name, List<Category> childCategories, List<Category> parentCategories) {
        this.name = name;
        this.children = childCategories;
        this.parent = parentCategories;
    }


}
