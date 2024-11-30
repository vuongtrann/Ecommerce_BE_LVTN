package com.spring.ecommerce.lvtn.service;

import com.spring.ecommerce.lvtn.model.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User uploadAvatar(Long userId, MultipartFile avatar) throws IOException;
}
