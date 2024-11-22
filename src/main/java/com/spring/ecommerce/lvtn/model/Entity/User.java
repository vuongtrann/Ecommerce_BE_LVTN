package com.spring.ecommerce.lvtn.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.ecommerce.lvtn.utils.enums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document("Users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{
    @Id
    String id;
    String firstName;
    String lastName;
    String phone;

    @UniqueElements
    String email;
    String password;
    String userName;
    ERole role;

    @JsonIgnore
    boolean hasVerified;
    @JsonIgnore
    String forgotPasswordToken;
    @JsonIgnore
    String verificationToken;

    public User(@NotBlank(message = "First name is required") String firstName, @NotBlank(message = "Last name is required") String lastName, @NotBlank(message = "Phone is required") String phone, @NotBlank(message = "Email is required") @Email(message = "Email is invalid") String email, String encodedPassword, ERole role) {
    }
}
