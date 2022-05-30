package com.lti.upskill.userservice.service;

import com.lti.upskill.userservice.vo.Course;
import com.lti.upskill.userservice.vo.ResponseTemplateVO;
import com.lti.upskill.userservice.entity.User;
import com.lti.upskill.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.lti.upskill.userservice.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Logic to get user from database
        List<SimpleGrantedAuthority> roles = null;
        User user = userRepository.findByUsername(username);
        if (user != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));

            //using fully qualified name as 2 imports of same name class not allowed (User)
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), roles);
        }
        throw new UsernameNotFoundException("user : " + username + " is not found !");

    }

    public User saveUser(UserModel userModel) {
        User newUser = new User();
        newUser.setFirstName(userModel.getFirstName());
        newUser.setLastName(userModel.getLastName());
        newUser.setUsername(userModel.getUsername());
        newUser.setEmail(userModel.getEmail());
        newUser.setRole(userModel.getRole());
        newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));    // this is the plain user provided pswd which we are  encrypting

        log.info("Inside saveUser of UserService");
        userRepository.save(newUser);
        return newUser;
    }


    public User findUserById(Long userId) {
        log.info("Inside findUserById of UserService");
        Optional<User> result = userRepository.findByUserId(userId);
        return result.orElse(null);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        userRepository.delete(findUserById(userId));

    }


    public ResponseTemplateVO getUserWithCourse(Long userId) {
        log.info("Inside getUserWithCourse of userService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        //User user = userRepository.findByUserId(userId);
        Optional<User> user = userRepository.findByUserId(userId);
        //return user.orElse(null);

        if (user.isPresent()) {
            User retrievedUser = user.get();

            //sending rest call to course microservice using the url
            Course course = restTemplate.getForObject("http://COURSE-SERVICE/courses/" + retrievedUser.getCourseId(), Course.class);
            vo.setUser(retrievedUser);
            vo.setCourse(course);

            return vo;
        }
        else{
            return null;}
    }
}