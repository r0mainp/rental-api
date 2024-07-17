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

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private RentalService rentalService;

    public MessageService(MessageRepository messageRepository, RentalService rentalService){
        this.messageRepository = messageRepository;
        this.rentalService = rentalService;
    }

    // Creates a new message and saves it in DB
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
