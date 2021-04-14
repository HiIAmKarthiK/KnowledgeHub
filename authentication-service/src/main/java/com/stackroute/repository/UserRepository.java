package com.stackroute.repository;

import com.stackroute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*This is a repository class, which manages the data using JpaRepository.*/
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /*This method is used to find the user based on his email and password.*/
    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}