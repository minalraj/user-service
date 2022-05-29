package com.lti.upskill.userservice;

import com.lti.upskill.userservice.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private TestController testController;

	@Test
	void contextLoads()throws Exception {
		assertThat(testController).isNotNull();
	}
}
