package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.ChangePasswordForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.ChangeUserNameForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.LoginForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.RegisterForm;
import com.spring.ecommerce.lvtn.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
//    private final UserRepository userRepository;
//    private final MailService mailService;
//    private final JwtUntil jwtUntil;
//    private final PasswordEncoder passwordEncoder;

    private final AuthService authservice;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterForm form) {
       authservice.register(form);
       return ResponseEntity.ok("Register successfully");
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity verify(@PathVariable String token) {
        authservice.verifyEmail(token);
        return ResponseEntity.ok("Verify successfully");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(authservice.login(form));
    }

    @PostMapping("/change-username")
    public ResponseEntity changeUserName(@RequestBody ChangeUserNameForm form) {
        authservice.changeUserName(form.getEmail(), form.getUserName());
        return ResponseEntity.ok("Change username successfully");
    }
    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordForm form) {
        authservice.changePassword(form.getEmail(), form.getOldPassword(), form.getNewPassword());
        return ResponseEntity.ok("Change password successfully");
    }


}
