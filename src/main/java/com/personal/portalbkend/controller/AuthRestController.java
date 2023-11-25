package com.personal.portalbkend.controller;

import com.personal.portalbkend.common.ApiResponse;
import com.personal.portalbkend.security.dto.JwtRequest;
import com.personal.portalbkend.service.UserDetailsServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    private final UserDetailsServiceImpl userDetailsService;

    public AuthRestController(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    /***
     * Util para usuario sin token o con token expirado
     * @param authenticationRequest
     * @return
     */
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse authenticatedTokenAndRegistLogin(@RequestBody JwtRequest authenticationRequest) {
    return userDetailsService.resultOfLoadUserByEmail(authenticationRequest);


    }


}