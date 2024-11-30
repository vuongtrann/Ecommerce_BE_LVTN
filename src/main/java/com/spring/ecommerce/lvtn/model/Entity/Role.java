package com.spring.ecommerce.lvtn.model.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.ecommerce.lvtn.utils.enums.ERole;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("Roles")
public class Role extends BaseEntity {
    private ERole role;
}
