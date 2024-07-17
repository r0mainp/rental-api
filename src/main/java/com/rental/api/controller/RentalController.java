package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.RentalCreateDto;
import com.rental.api.dto.RentalUpdateDto;
import com.rental.api.model.Rental;
import com.rental.api.model.RentalResponse;
import com.rental.api.service.RentalService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RequestMapping("/api/rentals")
@RestController
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    // Fetch all rentals and returns them in a RentalResponse
    @GetMapping("")
    public ResponseEntity<RentalResponse> getAllRentals() {
        List<Rental> rentals = (List<Rental>) rentalService.getAllRentals();
        RentalResponse rentalResponse = new RentalResponse(rentals);
        return ResponseEntity.ok(rentalResponse);
    }

    // Fetch a rental by its id an return 200 or 404
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRental(@PathVariable("id") final Integer id) {
        Optional<Rental> fetchedRental = rentalService.getRentalById(id);
        
        return fetchedRental
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Accepts a RentalCreateDto via multipart/form-data to create a new rental, return 201
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Rental> createRental(@ModelAttribute RentalCreateDto rentalDto) {
        Rental newRental = rentalService.addRental(rentalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRental);
    }

    /*
     * Updates an existing rental
     * @RequestParam are used instead of @ModelAttribute to prevent bindings error
     * return 200;
     */
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
