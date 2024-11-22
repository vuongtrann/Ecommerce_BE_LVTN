package com.spring.ecommerce.lvtn.repository;

import com.spring.ecommerce.lvtn.model.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.swing.text.html.Option;
import java.util.Optional;

@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByVerificationToken(String token);
}
