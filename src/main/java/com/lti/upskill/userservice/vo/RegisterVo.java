package com.lti.upskill.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVo extends StatusVo{

    private long userId;
    private String email;
    private String userName;
}
