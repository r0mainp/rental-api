package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rental.api.dto.RentalCreateDto;
import com.rental.api.dto.RentalUpdateDto;
import com.rental.api.model.Rental;
import com.rental.api.response.GenericResponse;
import com.rental.api.response.RentalResponse;
import com.rental.api.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/**
 * Controller class for handling CRUD operations on rentals.
 */
@RequestMapping("/api/rentals")
@RestController
@Tag(name = "Rental API", description = "API for CRUD operations on rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    /**
     * Endpoint to fetch all rentals from the database.
     * 
     * @return ResponseEntity containing a RentalResponse with the list of rentals retrieved.
     */
    @GetMapping("")
    @Operation(
        summary = "Get all rentals",
        description = "Retrieve a list of all rental properties from the database"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "List of rentals retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponse.class))
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<RentalResponse> getAllRentals() {
        List<Rental> rentals = (List<Rental>) rentalService.getAllRentals();
        RentalResponse rentalResponse = new RentalResponse(rentals);
        return ResponseEntity.ok(rentalResponse);
    }

    /**
     * Endpoint to fetch a rental by its ID.
     * 
     * @param id ID of the rental to be retrieved.
     * @return ResponseEntity containing either the Rental found or a GenericResponse with an error message if not found.
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a rental by ID",
        description = "Retrieve a rental property by its ID from the database",
        parameters = @Parameter(name = "id", description = "ID of the rental to be retrieved", required = true)
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Rental found and returned successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Rental not found for the provided ID",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getRental(@PathVariable("id") final Integer id) {
        Optional<Rental> fetchedRental = rentalService.getRentalById(id);
        
        if (fetchedRental.isPresent()) {
            Rental rental = fetchedRental.get();
            return ResponseEntity.ok().body(rental);
        } else {
            GenericResponse response = new GenericResponse("Rental not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Endpoint to create a new rental.
     * 
     * @param name Name of the rental.
     * @param surface Surface area of the rental.
     * @param price Price of the rental.
     * @param picture Picture of the rental.
     * @param description Description of the rental.
     * @return ResponseEntity containing a GenericResponse indicating success or failure of the operation.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Create a new rental",
        description = "Create a new rental property with the provided details",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the rental to be created",
            required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = RentalCreateDto.class))
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Rental created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<GenericResponse> createRental(
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

        rentalService.addRental(rentalDto);
        GenericResponse response = new GenericResponse("Rental created !");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to update an existing rental.
     * 
     * @param id ID of the rental to be updated.
     * @param name Updated name of the rental.
     * @param surface Updated surface area of the rental.
     * @param price Updated price of the rental.
     * @param description Updated description of the rental.
     * @return ResponseEntity containing a GenericResponse indicating success or failure of the operation.
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
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Rental updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
        )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<GenericResponse> updateRental(
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

        rentalService.updateRental(id, rentalDto);
        GenericResponse response = new GenericResponse("Rental updated !");
        return ResponseEntity.ok(response);
    }
    
}
