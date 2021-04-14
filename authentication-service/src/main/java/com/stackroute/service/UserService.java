package com.stackroute.service;

import com.stackroute.model.User;

public interface UserService {

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}