package com.lti.upskill.userservice.controller;

import com.lti.upskill.userservice.vo.ResponseTemplateVO;
import com.lti.upskill.userservice.entity.Status;
import com.lti.upskill.userservice.entity.User;
//import com.lti.upskill.userservice.event.RegistrationCompleteEvent;
import com.lti.upskill.userservice.model.JWTRequest;
import com.lti.upskill.userservice.model.JWTResponse;
import com.lti.upskill.userservice.model.UserModel;
import com.lti.upskill.userservice.repository.UserRepository;
import com.lti.upskill.userservice.service.UserService;
import com.lti.upskill.userservice.service.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/users")
public class MainController {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/hello")
    public String hello() {

        logger.info("Welcome to Upskill");
        return " Hello,  Welcome to Upskill Learning Platform!!";
    }

    @RequestMapping("/hellouser")
    public String helloUser() {
        return "Hello User";
    }

    @RequestMapping("/helloadmin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()

                    )
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new JWTResponse(token);
    }


    @CrossOrigin
    @PostMapping("/register")
    public Status saveUser(@RequestBody UserModel userModel) throws Exception {

        //check if user already exists
        List<User> users = userRepository.findAll();

        for (User user1 : users) {
            if (user1.equals(userModel)) {
                System.out.println("User Already exists!");
                return Status.USER_ALREADY_EXISTS;
            }
        }
        log.info("Inside save user of user controller");
        userDetailsService.saveUser(userModel);
       // return user;
        return Status.SUCCESS;
    }

//    @CrossOrigin()
//    @PostMapping("/login")
//    public Status loginUser(@Valid @RequestBody User user) {
//        List<User> users = userRepository.findAll();
//
//        for (User other : users) {
//            if (other.equals(user)) {
//                user.setLoggedIn(true);
//                userRepository.save(user);
//                return Status.SUCCESS;
//            }
//        }
//
//        return Status.FAILURE;
//    }
//
//
//    @CrossOrigin()
//    @PostMapping("/logout")
//    public Status logUserOut(@Valid @RequestBody User user) {
//        List<User> users = userRepository.findAll();
//
//        for (User other : users) {
//            if (other.equals(user)) {
//                user.setLoggedIn(false);
//                userRepository.save(user);
//                return Status.SUCCESS;
//            }
//        }
//
//        return Status.FAILURE;
//    }
//
//    @CrossOrigin()
//    @DeleteMapping("/delete/{id}")
//    public Status deleteUsers(@PathVariable(required=false,name="id") Long id) {
//        userDetailsService.deleteUser(id);
//        return Status.SUCCESS;
//    }


    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    public @ResponseBody User findUserById(@PathVariable(required = false, name = "userId") Long userId) {
        return userDetailsService.findUserById(userId);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public @ResponseBody Iterable<User> getAll() {
        return userDetailsService.getAllUsers();
    }


    @RequestMapping(value = "/response/{userId}", method = RequestMethod.GET)
    public ResponseTemplateVO getUserWithCourse(@PathVariable("userId") Long userId){
        log.info("Inside getUserWithCourse of User Controller");
        return userDetailsService.getUserWithCourse(userId);
    }



}





