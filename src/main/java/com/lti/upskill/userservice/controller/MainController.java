package com.lti.upskill.userservice.controller;


import com.lti.upskill.userservice.dto.LoginDto;
import com.lti.upskill.userservice.vo.RegisterVo;
import com.lti.upskill.userservice.vo.ResponseTemplateVO;
import com.lti.upskill.userservice.entity.User;
import com.lti.upskill.userservice.model.JWTRequest;
import com.lti.upskill.userservice.model.JWTResponse;
import com.lti.upskill.userservice.repository.UserRepository;
import com.lti.upskill.userservice.service.UserService;
import com.lti.upskill.userservice.service.utility.JWTUtility;
import com.lti.upskill.userservice.vo.StatusVo;
import com.lti.upskill.userservice.vo.UserVo;
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


//    @CrossOrigin
//    @PostMapping("/register")
//    public Status saveUser(@RequestBody User user) throws Exception {
//
//        //check if user already exists
//        List<User> users = userRepository.findAll();
//
//        for (User user1 : users) {
//            if (user1.equals(user)) {
//                System.out.println("User Already exists!");
//                return Status.USER_ALREADY_EXISTS;
//            }
//        }
//        log.info("Inside save user of user controller");
//        userDetailsService.saveUser(user);
//       // return user;
//        return Status.SUCCESS;
//    }

    //tetsing vo
    @CrossOrigin
    @PostMapping("/register")
    public UserVo saveUser(@RequestBody User user){

        log.info("Inside save user of user controller");
        UserVo userVo = new UserVo();
        try {
            User registeredUser = userDetailsService.saveUser(user);
            userVo.setUserId(registeredUser.getUserId());
            userVo.setFirstName(registeredUser.getFirstName());
            userVo.setLastName(registeredUser.getLastName());
            userVo.setUserName(registeredUser.getUserName());
            userVo.setEmail(registeredUser.getEmail());
            userVo.setPassword(registeredUser.getPassword());
            userVo.setRole(registeredUser.getRole());
            userVo.setMessage("Registered Successfully!!");
            userVo.setStatus(StatusVo.statusType.SUCCESS);
            return userVo;

        } catch (Exception e) {
            userVo.setMessage(e.getMessage());
            userVo.setStatus(StatusVo.statusType.FAILED);
            return userVo;
        }
    }

//    @CrossOrigin()
//    @DeleteMapping("/delete/{id}")
//    public Status deleteUsers(@PathVariable(required=false,name="id") Long id) {
//        userDetailsService.deleteUser(id);
//        return Status.SUCCESS;
//    }


//    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
//    public @ResponseBody User findUserById(@PathVariable(required = false, name = "userId") Long userId) {
//        return userDetailsService.findUserById(userId);
//    }

    //testing vo
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    public @ResponseBody UserVo findUserById(@PathVariable(required = false, name = "userId") Long userId) {
        log.info("Inside findUserById of User Controller");
        UserVo userVo = new UserVo();
        try {
            User retrievedUser = userDetailsService.findUserById(userId);
            userVo.setUserId(retrievedUser.getUserId());
            userVo.setFirstName(retrievedUser.getFirstName());
            userVo.setLastName(retrievedUser.getLastName());
            userVo.setUserName(retrievedUser.getUserName());
            userVo.setEmail(retrievedUser.getEmail());
            userVo.setPassword(retrievedUser.getPassword());
            userVo.setRole(retrievedUser.getRole());
            userVo.setMessage("User Retrieved Successfully");
            userVo.setStatus(StatusVo.statusType.SUCCESS);
            userVo.setUserStatus(retrievedUser.isUserStatus());
            return userVo;
        } catch (Exception e) {
            userVo.setMessage(e.getMessage());
            userVo.setStatus(StatusVo.statusType.FAILED);
            return userVo;
        }

    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public @ResponseBody Iterable<User> getAll() {
        return userDetailsService.getAllUsers();
    }

    @GetMapping("/deactivateUserById")
    public UserVo deactivateUserById(@PathVariable(required = false, name = "userId") Long userId) {
        log.info("Inside deactivateUserById of User Controller");
        UserVo userVo = new UserVo();
        try {
            User deactivateUser = userDetailsService.deactivateUserById(userId);
            userVo.setMessage("User Deactivated Successfully!!");
            userVo.setUserId(deactivateUser.getUserId());
            userVo.setUserName(deactivateUser.getUserName());
            userVo.setStatus(StatusVo.statusType.SUCCESS);
            return userVo;
        } catch (Exception e) {
            userVo.setMessage(e.getMessage());
            userVo.setStatus(StatusVo.statusType.FAILED);
            return userVo;
        }
    }

    @PostMapping("/login")
    public RegisterVo userLogin(@RequestBody LoginDto loginDto) {
        log.info("Inside userLogin of User Controller");
        RegisterVo registerVo = new RegisterVo();
        try {
            User loginUser = userDetailsService.userLogin(loginDto);
            registerVo.setEmail(loginUser.getEmail());
            registerVo.setMessage("User Login Successful!!");
            registerVo.setStatus(StatusVo.statusType.SUCCESS);
            registerVo.setUserId(loginUser.getUserId());
            registerVo.setUserName(loginUser.getUserName());
            return registerVo;
        } catch (Exception e) {
            registerVo.setMessage(e.getMessage());
            registerVo.setStatus(StatusVo.statusType.FAILED);
            return registerVo;
        }
    }

    @RequestMapping(value = "/response/{userId}", method = RequestMethod.GET)
    public ResponseTemplateVO getUserWithCourse(@PathVariable("userId") Long userId){
        log.info("Inside getUserWithCourse of User Controller");
        return userDetailsService.getUserWithCourse(userId);
    }



}





