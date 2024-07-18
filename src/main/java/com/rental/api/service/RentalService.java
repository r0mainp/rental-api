package com.rental.api.service;

import java.util.Optional;
import java.util.UUID;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.api.dto.RentalCreateDto;
import com.rental.api.dto.RentalUpdateDto;
import com.rental.api.model.Rental;
import com.rental.api.model.User;
import com.rental.api.repository.RentalRepository;

import jakarta.persistence.EntityNotFoundException;

import java.net.URL;

/**
 * Service class for managing rental operations.
 */
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private S3Service s3Service;

    private String picturePath;
    
    /**
     * Constructs a RentalService instance with necessary dependencies.
     *
     * @param rentalRepository The repository for Rental entities.
     * @param s3Service The service for interacting with AWS S3.
     */
    public RentalService(RentalRepository rentalRepository, S3Service s3Service){
        this.rentalRepository = rentalRepository;
        this.s3Service = s3Service;
    }

    /**
     * Retrieves all rentals from the database.
     *
     * @return Iterable collection of Rental entities.
     */
    public Iterable<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Retrieves a rental by its ID from the database.
     *
     * @param id The ID of the rental to retrieve.
     * @return Optional containing the found Rental entity, or empty if not found.
     */
    public Optional<Rental> getRentalById(final Integer id) {
        return rentalRepository.findById(id);
    }

    /**
     * Creates a new rental based on the provided DTO.
     *
     * @param input The DTO containing details of the rental to be created.
     * @return The created Rental entity.
     */
    public Rental addRental(RentalCreateDto input){
        
        MultipartFile file = input.getPicture();
        
        // Upload file to S3 bucket and get file path
        if (file != null && !file.isEmpty()) {
            String keyName =UUID.randomUUID().toString() + "_" + sanitizeFilename(file.getOriginalFilename()); // Create unique name
            URL fileUrl = s3Service.uploadAndGetFileUrl(keyName, file); // upload and retrieve URL
            picturePath = fileUrl.toString(); // set path
        }

        // Get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Create a new Rental based on input
        Rental newRental = new Rental.Builder()
            .setName(input.getName())
            .setSurface(input.getSurface())
            .setPrice(input.getPrice())
            .setDescription(input.getDescription())
            .setPicture(picturePath)
            .setOwner(currentUser)
            .build();
        return rentalRepository.save(newRental);
    }

    /**
     * Updates an existing rental with new information.
     *
     * @param id The ID of the rental to update.
     * @param input The DTO containing updated details of the rental.
     * @return The updated Rental entity.
     * @throws EntityNotFoundException if the specified rental ID does not exist.
     */
    public Rental updateRental(Integer id, RentalUpdateDto input) {

        //Get rental based on id
        Rental existingRental = getRentalById(id)
            .orElseThrow(() -> new EntityNotFoundException("Rental not found with id " + id));

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Create a new rental based on existing data and input
        Rental updatedRental = new Rental.Builder()
            .setName(input.getName())
            .setSurface(input.getSurface())
            .setPrice(input.getPrice())
            .setPicture(existingRental.getPicture())
            .setDescription(input.getDescription())
            .setOwner(currentUser)
            .build();

        // Ensure the new object retains the original id
        updatedRental.setId(existingRental.getId());

        return rentalRepository.save(updatedRental);
    }
    
    /**
     * Sanitizes the given filename by replacing illegal characters with underscores.
     *
     * @param filename The filename to sanitize.
     * @return The sanitized filename.
     */
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.\\-_]", "_"); // Replace illegal characters
    }
}
