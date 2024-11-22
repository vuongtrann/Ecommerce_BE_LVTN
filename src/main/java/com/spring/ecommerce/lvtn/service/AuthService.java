package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.LoginForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.RegisterForm;

public interface AuthService {
    public void register(RegisterForm registerForm);
    public String login(LoginForm loginForm);
    public void verifyEmail(String token);
    public void resendVerifyEmail(String email);
    public void changePassword(String email, String oldPassword, String newPassword);
    public String generateVerificationToken(String email);
    public void logout(String token);
    public void forgotPassword(String email);
    public void resetPassword(String token, String newPassword);
    public void changeUserName(String email, String newUserName);
}
