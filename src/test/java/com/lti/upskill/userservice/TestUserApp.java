package com.lti.upskill.userservice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestUserApp extends UserServiceApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUser() throws Exception {
        mockMvc.perform(get("/testuser")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value("Andy"))
                .andExpect(jsonPath("$.lastName").value("Allen"))
                .andExpect(jsonPath("$.userName").value("andyallen"))
                .andExpect(jsonPath("$.password").value("andy123"))
                .andExpect(jsonPath("$.email").value("andy.allen@gmail.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }
}
