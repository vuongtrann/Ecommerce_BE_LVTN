package com.spring.ecommerce.lvtn.model.Dao.Request.Auth;

import lombok.Data;

@Data
public class ChangePasswordForm {
    private String email;
    private String oldPassword;
    private String newPassword;
}
