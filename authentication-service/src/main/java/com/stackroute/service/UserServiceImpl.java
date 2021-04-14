package com.stackroute.service;

import com.stackroute.model.User;
import com.stackroute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*This is the service class, that handles login process. */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    /*The Repository class is injected into the User service class by @Autowired annotation .*/
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*This is method helps in finding the Registered user using the user credentials. */
    public User findByEmailAndPassword(String email, String password) {
        User foundUser = userRepository.findByEmailAndPassword(email, password);
        return foundUser;
    }

    /*This is method helps in finding the Registered user using the user email. */
    public User findByEmail(String email) {
        User foundUser = userRepository.findByEmail(email);
        return foundUser;
    }
}