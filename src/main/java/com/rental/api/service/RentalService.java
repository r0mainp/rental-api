package com.rental.api.service;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
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

import java.io.IOException;
import java.nio.file.Path;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    public RentalService(RentalRepository rentalRepository){
        this.rentalRepository = rentalRepository;
    }

    // Gets all rental
    public Iterable<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get a rental by id
    public Optional<Rental> getRentalById(final Integer id) {
        return rentalRepository.findById(id);
    }

    // Creates a new rental
    public Rental addRental(RentalCreateDto input){
        // TODO: use S3 buck to store image instead of local folder
        String picturePath = null;
        if (input.getPicture() != null && !input.getPicture().isEmpty()) {
            picturePath = saveFile(input.getPicture());
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

    // Update a specific rental
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
    
    // TODO: Remove when files are handled with S3 bucket
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.\\-_]", "_"); // Replace illegal characters
    }

    private String saveFile(MultipartFile file) {
        try {
            // Create the upload directory if it does not exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique file name
            String originalFilename = file.getOriginalFilename();
            String sanitizedFilename = sanitizeFilename(originalFilename);
            String fileName = System.currentTimeMillis() + "_" + sanitizedFilename;
            Path filePath = uploadPath.resolve(fileName);

            // Save the file to the specified path
            file.transferTo(filePath.toFile());

            // Return the file path as a string
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }


}
