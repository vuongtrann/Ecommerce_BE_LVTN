package com.spring.ecommerce.lvtn.controller;

import com.spring.ecommerce.lvtn.model.Dao.Respone.ApiResponse;
import com.spring.ecommerce.lvtn.model.Entity.User;
import com.spring.ecommerce.lvtn.service.UserService;
import com.spring.ecommerce.lvtn.utils.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("{id}/upload/avatar")
    public ResponseEntity<ApiResponse<User>> uploadAvatar(@PathVariable String id,@RequestParam("avatar") MultipartFile avatar) throws IOException {
        return ResponseEntity.ok(ApiResponse.builderResponse(
                SuccessCode.UPLOADED,
                userService.uploadAvatar(id,avatar)
        ));

    }
}
