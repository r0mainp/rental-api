package com.rental.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rental.api.model.User;
import com.rental.api.repository.UserRepository;

/**
 * Service class for performing operations related to users.
 */
@Service
public class UserService {
    private UserRepository userRepository;

    /**
     * Constructs a UserService instance with the provided UserRepository.
     *
     * @param userRepository The repository used for accessing user data.
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, or empty if not found.
     */
    public Optional<User> getUserById(final Integer id) {
        return userRepository.findById(id);
    }
}
