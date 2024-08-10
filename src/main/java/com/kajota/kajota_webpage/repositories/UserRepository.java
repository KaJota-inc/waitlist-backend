package com.kajota.kajota_webpage.repositories;

import com.kajota.kajota_webpage.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    boolean existsUserByEmail(String email);
    void deleteUserByEmail(String email);
}
