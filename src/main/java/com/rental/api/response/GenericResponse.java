package com.rental.api.response;

/**
 * GenericResponse class represents a generic response message.
 */
public class GenericResponse {
    private String message;

    /**
     * Constructs a new GenericResponse object with the provided message.
     * 
     * @param message The message content of the response
     */
    public GenericResponse(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message content of the response.
     * 
     * @return The message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content of the response.
     * 
     * @param message The message content to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}