package com.lti.upskill.userservice.repository;

import com.lti.upskill.userservice.entity.User;
import com.lti.upskill.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository mockUserRepository;

    @Autowired
    private PasswordEncoder mockPasswordEncoder;

    @Autowired
    private UserService mockUserDetailsService;

    @Test
    void findByUserId() {
        User testUser = new User();
        testUser.setUserId(2L);
        testUser.setFirstName("Andy");
        testUser.setLastName("Allen");
        testUser.setUserName("andyallen");
        testUser.setEmail("andy.allen@gmail.com");
        testUser.setRole("USER");
        testUser.setPassword(mockPasswordEncoder.encode("andy123"));
        mockUserRepository.save(testUser);
        when(mockUserRepository.findById(testUser.getUserId())).thenReturn(Optional.of(testUser));
        long userId = 2;
        assertEquals(testUser, mockUserDetailsService.findUserById(userId));

    }

//    @Test
//    void findByUserName() {
//    }


    @BeforeEach
    void setUp() {

        System.out.println("Setting up");
        mockUserRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {

        System.out.println("Tearing down");

    }
}