package com.spring.ecommerce.lvtn.service.IMPL;

import com.google.cloud.storage.*;
import com.spring.ecommerce.lvtn.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadFileServiceIMPL implements UploadFileService {
    private final Storage storage ;

//    @Value("${spring.google.cloud.storage.bucket}")
    private String bucketName = "vuongbucket";

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, String keyName, String folder) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);  // Tạo 5 luồng song song
        List<Callable<String>> tasks = files.stream()
                .map(file -> (Callable<String>) () -> uploadFile(file, keyName, folder))
                .toList();

        // Thực thi các tác vụ song song
        List<Future<String>> results = executor.invokeAll(tasks);

        // Đóng executor
        executor.shutdown();

        // Lấy kết quả từ các tác vụ
        return results.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException e) {
                        e.getCause().printStackTrace();  // Xử lý lỗi nếu có
                        throw new RuntimeException("Error when uploading file",e);

                    }catch (InterruptedException e){
                       Thread.currentThread().interrupt();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
    @Override
    public String uploadFile(MultipartFile file, String nameKey, String folder) throws IOException {
        String fileExtension = file.getOriginalFilename() != null ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")) : "";
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = nameKey + "_" + timestamp + fileExtension;
        //tao thu muc voi ten la folder va file name
        String objectName = folder + "/" + fileName;

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
    }

    @Override
    public void deleteFileByUrl(String fileUrl) {
        // Kiểm tra nếu URL hợp lệ và có thể phân tích
        if (fileUrl != null && fileUrl.startsWith("https://storage.googleapis.com/")) {
            // Trích xuất objectName từ URL
            String objectName = fileUrl.replace("https://storage.googleapis.com/" + bucketName + "/", "");

            // Lấy BlobId từ tên file và bucket
            BlobId blobId = BlobId.of(bucketName, objectName);

            // Lấy đối tượng Blob từ Storage
            Blob blob = storage.get(blobId);

            if (blob != null) {
                // Xóa file
                blob.delete();
                System.out.println("File " + objectName + " has been deleted.");
            } else {
                System.out.println("File " + objectName + " not found.");
            }
        } else {
            System.out.println("Invalid file URL");
        }
    }
}
