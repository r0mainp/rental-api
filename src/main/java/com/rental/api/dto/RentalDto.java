package com.rental.api.dto;

import org.springframework.web.multipart.MultipartFile;

public class RentalDto {
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
}
