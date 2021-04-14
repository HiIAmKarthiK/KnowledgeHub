package com.stackroute.config;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*Service for Bcrypt : carries out encoding and decoding of passwords */
@Service
public class BcryptGenerator {

    /*validates password from client side with the password found in db and returns a boolean */
    public Boolean passwordDecoder(String currentPassword, String ExistingPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPassword, ExistingPassword)) {
            return true;
        } else {
            return false;
        }
    }
}