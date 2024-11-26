package com.spring.ecommerce.lvtn.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Identity;
import com.google.cloud.Policy;
import com.google.cloud.Role;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class GoogleCloudStorageConfig {
    String jsonPath = "src/main/resources/ecom-442616-7c56a8f5234b.json";
    String bucketName = "vuongbucket";
    @Bean
    public Storage storage() throws Exception {
        Storage storage = StorageOptions.newBuilder()
                .setProjectId("ecom-442616")
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(jsonPath)))
                .build()
                .getService();
        return storage;
    }

    @Bean
    public Boolean setPublicAccessToBucket() throws Exception {
        try {
            Storage storage = storage();
            Policy policy = storage.getIamPolicy(bucketName);
            Policy updatedPolicy = policy.toBuilder()
                    .addIdentity(Role.of("roles/storage.objectViewer"), Identity.allUsers())
                    .build();
            storage.setIamPolicy(bucketName, updatedPolicy);
            System.out.println("Public access granted to all users with 'roles/storage.objectViewer'.");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
