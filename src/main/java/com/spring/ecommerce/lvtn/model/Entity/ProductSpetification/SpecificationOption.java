package com.spring.ecommerce.lvtn.model.Entity.ProductSpetification;

import com.spring.ecommerce.lvtn.utils.enums.SpectificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SpecificationOption {

        private SpectificationType type;
        private String value;
}
