package com.lti.upskill.userservice.service;

import com.lti.upskill.userservice.dto.LoginDto;
import com.lti.upskill.userservice.exception.UserException;
import com.lti.upskill.userservice.vo.CourseVo;
import com.lti.upskill.userservice.vo.ResponseTemplateVO;
import com.lti.upskill.userservice.entity.User;
import com.lti.upskill.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

//    public UserService(UserRepository mockUserRepository) {
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Logic to get user from database
        List<SimpleGrantedAuthority> roles = null;
        User user = userRepository.findByUserName(username);
        if (user != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));

            //using fully qualified name as 2 imports of same name class not allowed (User)
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("user : " + username + " is not found !");

    }

//    public User saveUser(User user) {
//        User newUser = new User();
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setUserName(user.getUserName());
//        newUser.setEmail(user.getEmail());
//        newUser.setRole(user.getRole());
//        newUser.setPassword(passwordEncoder.encode(user.getPassword()));    // this is the plain user provided pswd which we are  encrypting
//
//        log.info("Inside saveUser of UserService");
//        userRepository.save(newUser);
//        return newUser;
//    }
    //tetsing vo
    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserException("EmailId is already registered");
        }
//        //check exists method
//        else if (userRepository.existsByUsername(user.getUserName()).isPresent()) {
//                throw new UserException("Username already exists");}
            else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserStatus(true);
            try {
                User savedUser = userRepository.save(user);
                System.out.println("Registration successful!");
                return savedUser;
            } catch (Exception e) {
                throw new UserException(e.getMessage());
            }
        }
    }


    public User findUserById(Long userId) {
        log.info("Inside findUserById of UserService");
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserException("User not found");
        }

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public void deleteUser(Long userId) {
//        userRepository.delete(findUserById(userId));
//
//    }

    public User deactivateUserById(Long userId) {
        log.info("Inside deactivateUserById of UserService");
        User user = findUserById(userId);
        user.setUserStatus(false);
        return userRepository.save(user);
    }


    public User userLogin(LoginDto loginDto) {
        log.info("Inside userLogin of UserService");
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (!user.isPresent()) {
            throw new UserException("Invalid email");
        } else {
            User loginUser = user.get();  //method in optional
            byte[] decodedBytes = Base64.getDecoder().decode(loginUser.getPassword());
            String decodedPassword = new String(decodedBytes);
            if (decodedPassword.equals(loginDto.getPassword())) {
                loginUser.setUserStatus(true);
                userRepository.save(loginUser);
                return loginUser;
            } else {
                throw new UserException("Invalid password");
            }
        }
    }


    public ResponseTemplateVO getUserWithCourse(Long userId) {
        log.info("Inside getUserWithCourse of userService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isPresent()) {
            User retrievedUserWithCourse = user.get();

            //sending rest call to course microservice using the url
            CourseVo course = restTemplate.getForObject("http://localhost:9002/courses/" + retrievedUserWithCourse.getCourseId(), CourseVo.class);
            vo.setUser(retrievedUserWithCourse);
            vo.setCourse(course);

            return vo;
        }
        else{
            return null;}
    }
}