package com.rental.api.dto;

/**
 * Data Transfer Object (DTO) representing user login credentials.
 */
public class LoginUserDto {
    private String email;
    private String password;

    /**
     * Retrieves the email address of the user.
     *
     * @return The user's email address
     */
    public String getEmail(){
        return email;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The user's password
     */
    public String getPassword(){
        return password;
    }

}
