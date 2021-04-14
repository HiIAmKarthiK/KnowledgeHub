package com.stackroute.service;

import com.stackroute.model.User;
import com.stackroute.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    User user;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        user = new User("k@123.com", "password");
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    @Test
    public void findByEmailAndPasswordShouldReturnUser() {
        when(userRepository.findByEmailAndPassword("k@123.com", "password")).thenReturn(user);
        User foundUser = userService.findByEmailAndPassword("k@123.com", "password");
        assertEquals(foundUser, user);
        verify(userRepository, times(1)).findByEmailAndPassword("k@123.com", "password");
    }

    @Test
    public void findByEmailAndPasswordShouldReturnNull() {
        when(userRepository.findByEmailAndPassword("k@123.com", "password")).thenReturn(null);
        User foundUser = userService.findByEmailAndPassword("k@123.com", "password");
        assertEquals(foundUser, null);
        verify(userRepository, times(1)).findByEmailAndPassword("k@123.com", "password");
    }
}