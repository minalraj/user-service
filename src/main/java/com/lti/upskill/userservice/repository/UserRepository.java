package com.lti.upskill.userservice.repository;

import com.lti.upskill.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //fetch all user data form DB by searching using user Id or username
    Optional<User> findByUserId(Long userId);

    User findByUsername(String username);






}