package com.rental.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.api.dto.RentalDto;
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



@RequestMapping("/api/rentals")
@RestController
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    @GetMapping("")
    public ResponseEntity<RentalResponse> getAllRentals() {
        List<Rental> rentals = (List<Rental>) rentalService.getAllRentals();
        RentalResponse rentalResponse = new RentalResponse(rentals);
        return ResponseEntity.ok(rentalResponse);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Rental> createRental(@ModelAttribute RentalDto rentalDto) {
        Rental newRental = rentalService.addRental(rentalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRental);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRental(@PathVariable("id") final Integer id) {
        Optional<Rental> fetchedRental = rentalService.getRentalById(id);

        return fetchedRental
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
