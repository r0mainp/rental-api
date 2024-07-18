package com.rental.api.dto;

/**
 * Data Transfer Object (DTO) representing user registration details.
 */
public class RegisterUserDto {
    private String email;
    private String password;
    private String name;

    /**
     * Retrieves the email address of the user.
     *
     * @return The email address
     */
    public String getEmail(){
        return email;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The user's name
     */
    public String getName(){
        return name;
    }
}
