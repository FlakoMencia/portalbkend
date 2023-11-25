package com.personal.portalbkend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.portalbkend.common.ApiResponse;
import com.personal.portalbkend.common.Constants;
import com.personal.portalbkend.security.dto.JwtRequest;
import com.personal.portalbkend.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticatedTokenAndRegistLogin()  throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setEmail("admin@gmail.com");
        jwtRequest.setPassword("123"); //Solo en entorno de desarrollo
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));

    }
}