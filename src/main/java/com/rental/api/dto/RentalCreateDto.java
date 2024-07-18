package com.rental.api.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object (DTO) representing the details required to create a rental.
 */
public class RentalCreateDto {
    private String name;
    private int surface;
    private double price;
    private MultipartFile picture;
    private String description;

    /**
     * Retrieves the name of the rental property.
     *
     * @return The name of the rental
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the surface area of the rental property.
     *
     * @return The surface area in square units
     */
    public int getSurface() {
        return surface;
    }

    /**
     * Retrieves the price of the rental property.
     *
     * @return The price per unit of time (e.g., per month)
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the picture of the rental property.
     *
     * @return The picture file of the rental
     */
    public MultipartFile getPicture() {
        return picture;
    }

    /**
     * Retrieves the description of the rental property.
     *
     * @return The description of the rental
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the name of the rental property.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the surface area of the rental property.
     *
     * @param surface The surface area to set
     */
    public void setSurface(int surface) {
        this.surface = surface;
    }

    /**
     * Sets the price of the rental property.
     *
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the description of the rental property.
     *
     * @param description The description to set
     */
    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
