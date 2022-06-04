package com.lti.upskill.userservice.vo;
//VO - value objects

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVo {

    private Long courseId;
    private String courseName;
    private String courseDetails;
    private String courseDuration;
}
