package com.rental.api.model;

import java.util.Date;

/**
 * Represents a response object containing user details.
 * <p>
 * It is typically used to serialize user information in API responses.
 * </p>
 */
public class UserDetailsResponse {
    private Integer id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Constructs a new UserDetailsResponse object with the specified details.
     *
     * @param id        The unique identifier of the user
     * @param name      The name of the user
     * @param email     The email address of the user
     * @param createdAt The timestamp when the user was created
     * @param updatedAt The timestamp when the user was last updated
     */
    public UserDetailsResponse(Integer id, String name, String email, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's identifier
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The user's identifier to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The user's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The user's email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the timestamp when the user was created.
     *
     * @return The timestamp of user creation
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the user was created.
     *
     * @param createdAt The timestamp of user creation to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the timestamp when the user was last updated.
     *
     * @return The timestamp of last user update
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the user was last updated.
     *
     * @param updatedAt The timestamp of last user update to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    
}
