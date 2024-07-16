package com.rental.api.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private int surface;

    @Column(nullable = false)
    private double price;

    @Column
    private String picture;

    @Column(nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
    private Rental() {} // Use builder instead

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSurface() {
        return surface;
    }

    public double getPrice() {
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
        private int surface;
        private double price;
        private String picture;
        private String description;
        private User owner;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    
        public Builder setSurface(int surface) {
            this.surface = surface;
            return this;
        }
    
        public Builder setPrice(double price) {
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

        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Rental build() {
            Rental rental = new Rental();
            rental.name = this.name;
            rental.surface = this.surface;
            rental.price = this.price;
            rental.picture = this.picture;
            rental.description = this.description;
            rental.owner = this.owner;
            return rental;
        }
    }

    
}
