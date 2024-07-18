package com.rental.api.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a rental property entity.
 */
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
    @JsonProperty("created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    
    /**
     * Private constructor to prevent direct instantiation. Use Builder pattern instead.
     */
    private Rental() {}

    /**
     * Retrieves the ID of the rental property.
     * 
     * @return The ID of the rental property
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retrieves the name of the rental property.
     * 
     * @return The name of the rental property
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the surface area of the rental property.
     * 
     * @return The surface area of the rental property
     */
    public int getSurface() {
        return surface;
    }

    /**
     * Retrieves the price of the rental property.
     * 
     * @return The price of the rental property
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the picture URL or path of the rental property.
     * 
     * @return The picture URL or path of the rental property
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Retrieves the description of the rental property.
     * 
     * @return The description of the rental property
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the creation timestamp of the rental property.
     * 
     * @return The creation timestamp of the rental property
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Retrieves the last update timestamp of the rental property.
     * 
     * @return The last update timestamp of the rental property
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Retrieves the ID of the owner associated with the rental property.
     * 
     * @return The ID of the owner associated with the rental property, or null if no owner is set
     */
    @JsonProperty("owner_id")
    public Integer getOwnerId() {
        return owner != null ? owner.getId() : null;
    }

    /**
     * Sets the ID of the rental property.
     *
     * @param id The ID to set for the rental property
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Builder class for constructing instances of {@link Rental}.
     */
    public static class Builder{
        private String name;
        private int surface;
        private double price;
        private String picture;
        private String description;
        private User owner;

        /**
         * Sets the name of the rental property.
         * 
         * @param name The name of the rental property
         * @return The Builder instance
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
    
        /**
         * Sets the surface area of the rental property.
         * 
         * @param surface The surface area of the rental property
         * @return The Builder instance
         */
        public Builder setSurface(int surface) {
            this.surface = surface;
            return this;
        }
    
        /**
         * Sets the price of the rental property.
         * 
         * @param price The price of the rental property
         * @return The Builder instance
         */
        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }
    
        /**
         * Sets the picture URL or path of the rental property.
         * 
         * @param picture The picture URL or path of the rental property
         * @return The Builder instance
         */
        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }
    
        /**
         * Sets the description of the rental property.
         * 
         * @param description The description of the rental property
         * @return The Builder instance
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the owner of the rental property.
         * 
         * @param owner The owner of the rental property
         * @return The Builder instance
         */
        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        /**
         * Constructs and returns a new {@link Rental} object based on the Builder's current state.
         * 
         * @return A new Rental object
         */
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
