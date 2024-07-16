package com.rental.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "rentals")
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surface;

    @Column(nullable = false)
    private String price;

    @Column
    private String picture;

    @Column(nullable = false)
    private String description;

    
    private Rental() {} // Use builder instead

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurface() {
        return surface;
    }

    public String getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder{
        private String name;
        private String surface;
        private String price;
        private String picture;
        private String description;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    
        public Builder setSurface(String surface) {
            this.surface = surface;
            return this;
        }
    
        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }
    
        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }
    
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Rental build() {
            Rental rental = new Rental();
            rental.name = this.name;
            rental.surface = this.surface;
            rental.price = this.price;
            rental.picture = this.picture;
            rental.description = this.description;
            return rental;
        }
    }

    
}
