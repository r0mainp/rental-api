package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.LoginUserDto;
import com.rental.api.dto.RegisterUserDto;
import com.rental.api.model.LoginResponse;
import com.rental.api.model.User;
import com.rental.api.service.AuthenticationService;
import com.rental.api.service.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private JwtService jwtService;
    private AuthenticationService authenticationService;

    public AuthenticationController (
        JwtService jwtService,
        AuthenticationService authenticationService
    ){
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse.Builder()
            .setToken(jwtToken)
            .setExpiresIn(jwtService.getExpirationTime())
            .build();

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*
            Handle case where user tries to access /me without being authenticated 
            (Error in terminal when casting `authentication.getPrincipal()` to User class)
        */ 
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}
