package com.lti.upskill.userservice.repository;

import com.lti.upskill.userservice.entity.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
@EntityScan
public interface UserRepository extends JpaRepository<User, Long> {

    //fetch all user data form DB by searching using user Id or userName
    Optional<User> findByUserId(Long userId);

    User findByUserName(String userName);

    public Optional<User> findByEmail(String emailId);

  //  Optional<Object> existsByUsername(String userName);
}