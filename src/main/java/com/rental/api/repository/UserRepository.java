package com.rental.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rental.api.model.User;

/**
 * Repository interface for accessing User entities in the database.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    /**
     * Finds a user by their email address.
     * 
     * @param email The email address of the user to find.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);
}
