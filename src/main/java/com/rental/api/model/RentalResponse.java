package com.rental.api.model;

import java.util.List;

public class RentalResponse {
    private List<Rental> rentals;

    public RentalResponse(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
