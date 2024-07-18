package com.rental.api.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rental.api.dto.MessageDto;
import com.rental.api.model.Message;
import com.rental.api.model.Rental;
import com.rental.api.model.User;
import com.rental.api.repository.MessageRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for managing messages related operations.
 */
@Service
public class MessageService {
    private MessageRepository messageRepository;
    private RentalService rentalService;

    /**
     * Constructs a MessageService instance with necessary dependencies.
     *
     * @param messageRepository The repository for Message entities.
     * @param rentalService The service for Rental operations.
     */
    public MessageService(MessageRepository messageRepository, RentalService rentalService){
        this.messageRepository = messageRepository;
        this.rentalService = rentalService;
    }

    /**
     * Creates a new message and saves it in the database.
     *
     * @param input The DTO containing details of the message to be created.
     * @return The created Message entity.
     * @throws EntityNotFoundException if the specified rental ID does not exist.
     */
    public Message addNewMessage(MessageDto input){

        // Get rental
        Optional<Rental> rentalOptional = rentalService.getRentalById(input.getRentalId());
        Rental currentRental = rentalOptional.orElseThrow(() -> new EntityNotFoundException("Rental not found with id " + input.getRentalId()));


        // Get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Message message = new Message.Builder()
            .setMessage(input.getMessage())
            .setUser(currentUser)
            .setRental(currentRental)
            .build();

        return messageRepository.save(message);
    }

}
