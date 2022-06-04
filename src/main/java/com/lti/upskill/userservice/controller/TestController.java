package com.lti.upskill.userservice.controller;


import com.lti.upskill.userservice.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @RequestMapping(value = "/testuser", method = RequestMethod.GET)
    public User userDetails() {

        User user = new User();

        user.setFirstName("Andy");
        user.setLastName("Allen");
        user.setUserName("andyallen");
        user.setPassword("andy123");
        user.setEmail("andy.allen@gmail.com");
        user.setRole("USER");
        user.setUserId(user.getUserId());
        return user;
    }
}
