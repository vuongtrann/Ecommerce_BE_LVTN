package com.spring.ecommerce.lvtn.service.IMPL;

import com.spring.ecommerce.lvtn.exception.AppException;
import com.spring.ecommerce.lvtn.model.Entity.User;
import com.spring.ecommerce.lvtn.repository.UserRepository;
import com.spring.ecommerce.lvtn.service.UploadFileService;
import com.spring.ecommerce.lvtn.service.UserService;
import com.spring.ecommerce.lvtn.utils.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserSericeIMPL implements UserService {
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;
    @Override
    public User uploadAvatar(String id, MultipartFile avatar) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String oldAvatar = user.getAvatar();
        if (oldAvatar != null){
            uploadFileService.deleteFileByUrl(oldAvatar);
        }
        String newAvatar = uploadFileService.uploadFile(avatar,user.getFirstName(),user.getFirstName());
        user.setAvatar(newAvatar);
        return userRepository.save(user);
    }
}
