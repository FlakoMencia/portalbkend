package com.personal.portalbkend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.portalbkend.security.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.token}")
    private String jwtToken;

    @Test
    void getUserListDepensOnExpireToken() throws Exception {
        try{
            if(jwtTokenUtil.isTokenExpired(jwtToken)){
                mockMvc.perform(MockMvcRequestBuilders.get("/api/user/list")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
            }
        }catch (ExpiredJwtException e){
            //Para cuando el token ha expirado
            mockMvc.perform(MockMvcRequestBuilders.get("/api/user/list")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());

        }
    }



}