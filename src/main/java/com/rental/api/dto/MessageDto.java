package com.rental.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDto {
    private String message;

    @JsonProperty("rental_id")
    private Integer rentalId;

    @JsonProperty("user_id")
    private Integer userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
  
}
