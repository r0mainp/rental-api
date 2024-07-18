package com.rental.api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rental.api.dto.LoginUserDto;
import com.rental.api.dto.RegisterUserDto;
import com.rental.api.model.User;
import com.rental.api.repository.UserRepository;


/**
 * Service class that handles user authentication and registration.
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * Constructs an AuthenticationService instance with the necessary dependencies.
     *
     * @param userRepository The repository for accessing user data.
     * @param authenticationManager The authentication manager for authenticating users.
     * @param passwordEncoder The password encoder for encoding user passwords.
     */
    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user with the provided details.
     *
     * @param input The details of the user to be registered.
     * @return The newly registered user.
     */
    public User signup(RegisterUserDto input){
        User user = new User.Builder()
            .setName(input.getName())
            .setEmail(input.getEmail())
            .setPassword(passwordEncoder.encode(input.getPassword()))
            .build();
        
        return userRepository.save(user);
    }

    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param input The login credentials of the user.
     * @return The authenticated user.
     * @throws RuntimeException if authentication fails or if the user is not found.
     */
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
