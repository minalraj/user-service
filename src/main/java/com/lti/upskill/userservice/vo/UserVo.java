package com.lti.upskill.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends StatusVo{

    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String role;
    private boolean userStatus;
}
