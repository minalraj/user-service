package com.lti.upskill.userservice.model;

import lombok.*;

@Data
//Generates getters, setters for all fields, a useful toString method, and hashCode and equals implementations that check all non-transient fields.
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String role;    
}
