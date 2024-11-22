package com.spring.ecommerce.lvtn.model.Entity.ProductVariant;

import com.spring.ecommerce.lvtn.utils.enums.VariantType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("VariantOptions")
public class VariantOption {
    @Id
    private String id;
    private VariantType type;
    private String value;

    public VariantOption(VariantType type, String value) {
        this.type = type;
        this.value = value;
    }
}
