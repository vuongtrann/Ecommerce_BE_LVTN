package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.ChangePasswordForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.ChangeUserNameForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.LoginForm;
import com.spring.ecommerce.lvtn.model.Dao.Request.Auth.RegisterForm;
import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Entity.User;
import com.spring.ecommerce.lvtn.service.AuthService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
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
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid RegisterForm form) {
       User user = authservice.register(form);
       return ResponseEntity.ok( ApiResponse.builderResponse(
               SuccessCode.REGISTER, user
       ));
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<ApiResponse<String>> verify(@PathVariable String token) {
        authservice.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.builderResponse(
                SuccessCode.VERIFY_ACCOUNT, null
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(ApiResponse.builderResponse(
                SuccessCode.LOGIN, authservice.login(form)
        ));
    }

    @PostMapping("/change-username")
    public ResponseEntity<ApiResponse<String>> changeUserName(@RequestBody ChangeUserNameForm form) {
        authservice.changeUserName(form.getEmail(), form.getUserName());
        return ResponseEntity.ok(ApiResponse.builderResponse(
                SuccessCode.CHANGE_USERNAME, null
        ));
    }
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordForm form) {
        authservice.changePassword(form.getEmail(), form.getOldPassword(), form.getNewPassword());
        return ResponseEntity.ok(ApiResponse.builderResponse(
                SuccessCode.CHANGE_PASSWORD, null
        ));
    }


}
