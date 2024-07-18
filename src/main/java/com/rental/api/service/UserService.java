package com.rental.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rental.api.model.User;
import com.rental.api.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Get a user by id
    public Optional<User> getUserById(final Integer id) {
        return userRepository.findById(id);
    }
}
