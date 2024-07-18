package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.LoginUserDto;
import com.rental.api.dto.RegisterUserDto;
import com.rental.api.model.LoginResponse;
import com.rental.api.model.User;
import com.rental.api.service.AuthenticationService;
import com.rental.api.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RequestMapping("/api/auth")
@RestController
@Tag(name = "Authentication API", description = "API for user authentication and authorization")
@SecurityRequirements()
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
    @Operation(
        summary = "Register user",
        description = "Register a new user with the provided details",
        security = {}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User registered successfully", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticate user with the provided credentials and generate JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User authenticated successfully and JWT token generated", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            ),
        @ApiResponse(responseCode = "401", description = "Authentication failed due to invalid credentials")
    })
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
        @Operation(
        summary = "Get authenticated user",
        description = "Retrieve details of the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Authenticated user details retrieved successfully", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
        ),
        @ApiResponse(responseCode = "403", description = "Access denied due to missing or invalid authentication")
    })
    @SecurityRequirement(name = "bearerAuth")
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
