package com.spring.ecommerce.lvtn.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.ecommerce.lvtn.utils.enums.ERole;
import com.spring.ecommerce.lvtn.utils.enums.Status;
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
public class User{
    @Id
    String id;
    String firstName;
    String lastName;
    String phone;

    @UniqueElements(message = "Email already exists")
    String email;
    @JsonIgnore
    @NotBlank(message = "Password is required")
    String password;
    @UniqueElements(message = "Username already exists")
    String userName;
    ERole role;
    String avatar;

    Status status;

    @JsonIgnore
    boolean hasVerified;
    @JsonIgnore
    String forgotPasswordToken;
    @JsonIgnore
    String verificationToken;
    @JsonIgnore
    boolean isLocked;



    public User(@NotBlank(message = "First name is required") String firstName, @NotBlank(message = "Last name is required") String lastName, @NotBlank(message = "Phone is required") String phone, @NotBlank(message = "Email is required") @Email(message = "Email is invalid") String email, String encodedPassword, ERole role) {
    }
}
