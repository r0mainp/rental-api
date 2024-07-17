package com.rental.api.dto;

import org.springframework.web.multipart.MultipartFile;

public class RentalCreateDto {
    private String name;
    private int surface;
    private double price;
    private MultipartFile picture;
    private String description;

    public String getName() {
        return name;
    }

    public int getSurface() {
        return surface;
    }

    public double getPrice() {
        return price;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
