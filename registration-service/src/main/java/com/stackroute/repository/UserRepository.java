package com.stackroute.repository;

import com.stackroute.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*This is a repository class, which manages the data using MongoRepository.*/
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /*This method is used to find the given user  by the Email*/
    public User findByEmail(String Email);
}
