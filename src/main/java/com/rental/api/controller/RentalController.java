package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rental.api.dto.RentalCreateDto;
import com.rental.api.dto.RentalUpdateDto;
import com.rental.api.model.Rental;
import com.rental.api.model.RentalResponse;
import com.rental.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RequestMapping("/api/rentals")
@RestController
@Tag(name = "Rental API", description = "API for CRUD operations on rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    // Fetch all rentals and returns them in a RentalResponse
    @GetMapping("")
        @Operation(
        summary = "Get all rentals",
        description = "Retrieve a list of all rental properties from the database",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "List of rentals retrieved successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponse.class))
            )
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<RentalResponse> getAllRentals() {
        List<Rental> rentals = (List<Rental>) rentalService.getAllRentals();
        RentalResponse rentalResponse = new RentalResponse(rentals);
        return ResponseEntity.ok(rentalResponse);
    }

    // Fetch a rental by its id an return 200 or 404
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a rental by ID",
        description = "Retrieve a rental property by its ID from the database",
        parameters = @Parameter(name = "id", description = "ID of the rental to be retrieved", required = true),
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Rental found and returned successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))
            ),
            @ApiResponse(responseCode = "404", description = "Rental not found for the provided ID")
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Rental> getRental(@PathVariable("id") final Integer id) {
        Optional<Rental> fetchedRental = rentalService.getRentalById(id);
        
        return fetchedRental
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Accepts a RentalCreateDto via multipart/form-data to create a new rental, return 201
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Create a new rental",
        description = "Create a new rental property with the provided details",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the rental to be created",
            required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = RentalCreateDto.class))
        ),
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Rental created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))
            )
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Rental> createRental(
        @RequestParam String name,
        @RequestParam int surface,
        @RequestParam double price,
        @RequestParam MultipartFile picture,
        @RequestParam String description
    ) {
        RentalCreateDto rentalDto = new RentalCreateDto();
        rentalDto.setName(name);
        rentalDto.setSurface(surface);
        rentalDto.setPrice(price);
        rentalDto.setDescription(description);
        rentalDto.setPicture(picture);

        Rental newRental = rentalService.addRental(rentalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRental);
    }

    /*
     * Updates an existing rental
     * @RequestParam are used instead of @ModelAttribute to prevent bindings error
     * return 200;
     */
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Update an existing rental",
        description = "Update the details of an existing rental property by its ID",
        parameters = @Parameter(name = "id", description = "ID of the rental to be updated", required = true),
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated details of the rental",
            required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = RentalUpdateDto.class))
        ),
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Rental updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))
            )
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Rental> updateRental(
        @PathVariable Integer id, 
        @RequestParam String name,
        @RequestParam int surface,
        @RequestParam double price,
        @RequestParam String description
    ) {

        // Creates the new Dto used in service
        RentalUpdateDto rentalDto = new RentalUpdateDto();
        rentalDto.setName(name);
        rentalDto.setSurface(surface);
        rentalDto.setPrice(price);
        rentalDto.setDescription(description);

        Rental updatedRental = rentalService.updateRental(id, rentalDto);
        return ResponseEntity.ok(updatedRental);
    }
    
}
