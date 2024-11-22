package com.spring.ecommerce.lvtn.model.Dao.Request.Auth;

import lombok.Data;

@Data
public class ChangeUserNameForm {
    private String email;
    private String userName;
}
