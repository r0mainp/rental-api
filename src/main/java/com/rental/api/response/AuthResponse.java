package com.rental.api.response;

/**
 * AuthResponse class represents a response containing an authentication token.
 */
public class AuthResponse {
    private String token;

    /**
     * Constructs a new AuthResponse object with the provided token.
     * 
     * @param token The authentication token
     */
    public AuthResponse(String token) {
        this.token = token;
    }

    /**
     * Retrieves the authentication token.
     * 
     * @return The authentication token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the authentication token.
     * 
     * @param token The authentication token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
