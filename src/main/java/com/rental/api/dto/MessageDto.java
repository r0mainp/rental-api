package com.rental.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) representing a message to be sent or received.
 */
public class MessageDto {
    private String message;

    @JsonProperty("rental_id")
    private Integer rentalId;

    @JsonProperty("user_id")
    private Integer userId;

    /**
     * Retrieves the message content.
     *
     * @return The message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content.
     *
     * @param message The message content to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the ID of the associated rental.
     *
     * @return The ID of the rental
     */
    public Integer getRentalId() {
        return rentalId;
    }

    /**
     * Retrieves the ID of the associated user.
     *
     * @return The ID of the user
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the associated rental.
     *
     * @param rentalId The ID of the rental to set
     */
    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    /**
     * Sets the ID of the associated user.
     *
     * @param userId The ID of the user to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
