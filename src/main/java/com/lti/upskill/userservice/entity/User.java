package com.lti.upskill.userservice.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        @Column(nullable = false, unique = true, length = 40)
        private String email;

        @Column(nullable = false, length = 60)
        private String password;

        @Column(name = "first_name", nullable = false, length = 20)
        private String firstName;

        @Column(name = "last_name", nullable = false, length = 20)
        private String lastName;

        @Column(name="user_name", length = 20)
        private String userName;

        @Column(name = "Role", nullable = false, length = 10)
        private  String role;

        private Long courseId;

        private boolean userStatus;

}
