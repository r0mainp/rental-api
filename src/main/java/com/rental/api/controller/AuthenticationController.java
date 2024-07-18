package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.LoginUserDto;
import com.rental.api.dto.RegisterUserDto;
import com.rental.api.model.GenericResponse;
import com.rental.api.model.AuthResponse;
import com.rental.api.model.User;
import com.rental.api.model.UserDetailsResponse;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller class for handling authentication and authorization operations.
 */
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

    /**
     * Endpoint for user registration.
     * 
     * @param registerUserDto DTO containing user registration details.
     * @return ResponseEntity containing an AuthResponse with JWT token upon successful registration.
     */
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
        )
    })
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        String jwtToken = jwtService.generateToken(registeredUser);
        AuthResponse response = new AuthResponse(jwtToken);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for user authentication (login).
     * 
     * @param loginUserDto DTO containing user login credentials.
     * @return ResponseEntity containing an AuthResponse with JWT token upon successful authentication,
     *         or a GenericResponse with error details upon authentication failure.
     */
    @PostMapping("/login")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticate user with the provided credentials and generate JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User authenticated successfully and JWT token generated", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Authentication failed due to invalid credentials",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
        )
    })
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            String jwtToken = jwtService.generateToken(authenticatedUser);
            AuthResponse response = new AuthResponse(jwtToken);

            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException ex) {
            GenericResponse errorResponse = new GenericResponse("error");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Endpoint to retrieve details of the authenticated user.
     * 
     * @return ResponseEntity containing UserDetailsResponse with authenticated user details,
     *         or a GenericResponse with error details if authentication fails or user is unauthorized.
     */
    @GetMapping("/me")
    @Operation(
        summary = "Get authenticated user",
        description = "Retrieve details of the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Authenticated user details retrieved successfully", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailsResponse.class))
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Access denied due to missing or invalid authentication",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*
            Handle case where user tries to access /me without being authenticated 
            (Error in terminal when casting `authentication.getPrincipal()` to User class)
        */ 
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)) {
            GenericResponse errorResponse = new GenericResponse("Forbidden");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }

        User currentUser = (User) authentication.getPrincipal();

        UserDetailsResponse userDetails = new UserDetailsResponse(
            currentUser.getId(),
            currentUser.getName(),
            currentUser.getEmail(),
            currentUser.getCreatedAt(),
            currentUser.getUpdatedAt()
        );

        return ResponseEntity.ok(userDetails);
    }
}
