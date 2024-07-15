package com.rental.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.rental.api.service.JwtService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class LoginController {
    public JwtService jwtService;

    public LoginController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @PostMapping("/api/auth/login")
    public String getToken(Authentication authentication) {
        System.out.println(authentication);

       String token = jwtService.generateToken(authentication);
       return token;
    }
}
