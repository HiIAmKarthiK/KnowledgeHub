package com.stackroute.service;

import com.stackroute.exception.UserAlreadyExists;
import com.stackroute.exception.UserNotFound;
import com.stackroute.model.PreviousRead;
import com.stackroute.model.User;

import java.util.List;

/*This is the service class, where the abstract methods are declared. */

public interface UserService {
    /*This method will save the user details.*/
    User saveUser(User user) throws UserAlreadyExists;

    /* This method will Retrieve the list of users*/
    List<User> getAllUsers() throws UserNotFound;

    /*This method will update the notes through email*/
    User updateNotes(String email, PreviousRead previousRead) throws UserAlreadyExists;

    User updateLastPage(String email, PreviousRead previousRead) throws UserAlreadyExists;

    Integer getLastPage(String userEmail, String bookTitle);

    /*This method will Find the user by email*/
    User getUserByEmail(String email);

    /*This method will Retrieve all the notes*/
    List<PreviousRead> getAllNotes(String email);
    User getUserDetails(String email) throws UserNotFound;
    User changeRoleUser(String email) throws  UserNotFound;
}
