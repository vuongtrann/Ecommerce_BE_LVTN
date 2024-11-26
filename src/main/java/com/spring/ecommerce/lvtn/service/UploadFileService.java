package com.spring.ecommerce.lvtn.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadFileService {
    List<String> uploadFiles(List<MultipartFile> files, String keyName, String folder) throws InterruptedException;
    String uploadFile(MultipartFile file, String keyName, String folder) throws IOException;
    public void deleteFileByUrl(String fileUrl);
}
