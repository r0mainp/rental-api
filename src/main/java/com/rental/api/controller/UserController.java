package com.rental.api.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.model.GenericResponse;
import com.rental.api.model.User;
import com.rental.api.model.UserDetailsResponse;
import com.rental.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller class for handling operations related to users.
 */
@RequestMapping("/api/user")
@RestController
@Tag(name = "User API", description = "API for users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Endpoint to fetch a user by their ID.
     * 
     * @param id ID of the user to be retrieved.
     * @return ResponseEntity containing either the UserDetailsResponse with user details or a GenericResponse if user is not found.
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a user by ID",
        description = "Retrieve a user by its ID from the database",
        parameters = @Parameter(name = "id", description = "ID of the user to be retrieved", required = true)
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User found and returned successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailsResponse.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Unauthorized request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getUser(@PathVariable("id") final Integer id) {
        Optional<User> fetchedUser = userService.getUserById(id);
        
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            UserDetailsResponse userDetails = new UserDetailsResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            );
            return ResponseEntity.ok().body(userDetails);
        } else {
            GenericResponse response = new GenericResponse("Unauthorized");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
