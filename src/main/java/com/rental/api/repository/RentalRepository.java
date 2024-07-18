package com.rental.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rental.api.model.Rental;

/**
 * Repository interface for accessing Rental entities in the database.
 */
@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer>{
    /**
     * This interface inherits CRUD operations for Rental entities.
     */
}
