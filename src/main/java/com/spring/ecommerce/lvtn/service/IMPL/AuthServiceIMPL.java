package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.LoginForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.RegisterForm;
import com.spring.ecommerce.lvtn.model.Entity.User;
import com.spring.ecommerce.lvtn.repository.UserRepository;
import com.spring.ecommerce.lvtn.service.AuthService;
import com.spring.ecommerce.lvtn.service.MailService;
import com.spring.ecommerce.lvtn.utils.JWT.JwtUntil;
import com.spring.ecommerce.lvtn.utils.enums.Status;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final JwtUntil jwtUntil;
    private final PasswordEncoder passwordEncoder;

    @Value("${server.url}")
    String serverUrl;

    public void register(RegisterForm registerForm) {
        User user1 = userRepository.findByEmail(registerForm.getEmail());
        if (user1 != null) {
            throw new RuntimeException("Email already exists");
        }
        String hashPassword = passwordEncoder.encode(registerForm.getPassword());
        String token = generateVerificationToken(registerForm.getEmail());

        User user = new User();

        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setEmail(registerForm.getEmail());
        user.setUserName(registerForm.getEmail());
        user.setPhone(registerForm.getPhone());
        user.setPassword(hashPassword);
        user.setRole(registerForm.getRole());
        user.setHasVerified(false);
        user.setVerificationToken(token);
        user.setStatus(Status.INACTIVE);

        userRepository.save(user);
        String confirmationUrl = serverUrl+ "/api/v1/auth/verify/" + token;

        mailService.sendRegistrationConfirmMail(registerForm.getEmail(), confirmationUrl, registerForm.getFirstName(), registerForm.getLastName());


    }

    public String login(LoginForm loginForm) {
        User user = userRepository.findByUserName(loginForm.getUsername());
        if (user == null || !passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        if (!user.isHasVerified()) {
            throw new RuntimeException("Please verify your email");
        }
        String token = "Bearer " + jwtUntil.generateToken(user.getEmail());
        return  token;
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token);
        if(user == null){
            throw new RuntimeException("Invalid token");
        }
        user.setHasVerified(true);
        user.setVerificationToken(null);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    public void resendVerifyEmail(String email) {
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public String generateVerificationToken(String email) {
        return java.util.UUID.randomUUID().toString();
    }

    public void logout(String token) {
    }

    public void forgotPassword(String email) {
    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }

    @Override
    public void changeUserName(String email, String newUserName) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setUserName(newUserName);
        userRepository.save(user);
        // Gửi email xác nhận
        String subject = "Username Changed Successfully";
        String body = String.format("Hi %s,\n\nYour username has been successfully changed to %s.\n\nIf you did not request this change, please contact support immediately.",
                user.getEmail(), newUserName);
        mailService.sendMail(user.getEmail(), subject, body);
    }

}
