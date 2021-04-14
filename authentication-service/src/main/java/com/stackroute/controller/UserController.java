package com.stackroute.controller;

import com.stackroute.config.BcryptGenerator;
import com.stackroute.config.JWTGenerator;
import com.stackroute.exception.InvalidCredentials;
import com.stackroute.model.User;
import com.stackroute.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* This is a Controller class named UserController.
The class is annotated with @RestController, @RequestMapping, and @CrossOrigin annotations.
This class is responsible for authenticating the user credentials.
*/
@RestController
@CrossOrigin(value = "*")
@RequestMapping("api/v1/login")
public class UserController {

    private UserServiceImpl userService;
    private JWTGenerator jwtGenerator;
    private BcryptGenerator bcryptGenerator;

    /*The service class and JwtGenerator is injected into the controller by @Autowired annotation .*/
    @Autowired
    public UserController(UserServiceImpl userService, JWTGenerator jwtGenerator, BcryptGenerator bcryptGenerator) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.bcryptGenerator = bcryptGenerator;
    }

    /*This method Authenticate the user credentials.*/
    @PostMapping("/user")
    public ResponseEntity<?> getCredentials(@RequestBody User user) throws InvalidCredentials {
        ResponseEntity<?> responseEntity;
        User foundUser = userService.findByEmail(user.getEmail());
        boolean value = bcryptGenerator.passwordDecoder(user.getPassword(), foundUser.getPassword());

        if (value && foundUser != null) {
            return new ResponseEntity<>(jwtGenerator.generateJwtToken(foundUser), HttpStatus.OK);
        }
        throw new InvalidCredentials();
    }
}