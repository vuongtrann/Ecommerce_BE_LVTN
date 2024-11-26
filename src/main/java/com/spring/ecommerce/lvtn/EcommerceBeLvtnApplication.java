package com.spring.ecommerce.lvtn;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import javax.crypto.SecretKey;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SpringBootApplication
@EnableMongoRepositories
public class EcommerceBeLvtnApplication {
    @Autowired
    private  Environment environment;
    public static void main(String[] args) {
//        SpringApplication.run(EcommerceBeLvtnApplication.class, args);
        SpringApplication applicationContext = new SpringApplication(EcommerceBeLvtnApplication.class);
        Environment environment = applicationContext.run(args).getEnvironment();
        System.out.println("Application is running at port : " + environment.getProperty("server.port"));


//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Tạo khóa bí mật mạnh
//        String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
//        System.out.println("Generated Key (Base64): " + base64Key);
    }

}
