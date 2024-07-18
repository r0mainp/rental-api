package com.rental.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rental.api.model.Message;

/**
 * Repository interface for accessing Message entities in the database.
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{
    /**
     * This interface inherits CRUD operations for Message entities.
     */
}
