//package com.lti.upskill.userservice.service;
//
//import com.lti.upskill.userservice.entity.User;
//import com.lti.upskill.userservice.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock   //using mock instead of autowired so that actual data from DB is not fetched, instead a fake one is sued
//    private UserRepository mockUserRepository;
//
//    private UserService mockUserDetailsService;
//
//    @BeforeEach
//    void setUp() {
//        //sending fake data repo to actual user service to test this out,
//        // hence below setup ( also, we will not autowire to prevent actual data fetch )
//        this.mockUserDetailsService = new UserService(this.mockUserRepository);
//    }
//
//    @Test
//    void getAllUsers() {
////        mockUserDetailsService.getAllUsers();
////        verify(mockUserRepository).findAll();
//
//        User testUser1 = new User(20, "andy.allen@gmail.com", "andy123", "andy", "allen", "andyallen", "USER");
//        User testUser2 = new User();
//        List<User> userList = new ArrayList<User>();
//        userList.add(testUser1);
//        userList.add(testUser2);
//        Mockito.when(mockUserRepository.findAll()).thenReturn(userList);
//        assertEquals(userList, mockUserDetailsService.findAllUsers());
//    }
//}