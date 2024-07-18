package com.rental.api.model;

import java.util.List;

/**
 * Represents a response object containing a list of rental properties.
 */
public class RentalResponse {
    private List<Rental> rentals;

    /**
     * Constructs a new RentalResponse object with the specified list of rentals.
     *
     * @param rentals The list of rental properties to encapsulate in the response
     */
    public RentalResponse(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Retrieves the list of rental properties encapsulated in this response.
     *
     * @return The list of rental properties
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rental properties for this response.
     *
     * @param rentals The list of rental properties to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
