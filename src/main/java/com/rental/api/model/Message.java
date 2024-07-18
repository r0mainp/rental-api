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
 * Represents a message entity associated with a user and a rental.
 */
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    @JsonProperty("rental_id")
    private Rental rental;

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
    private Message(){}

    /**
     * Retrieves the creation timestamp of the message.
     * 
     * @return The creation timestamp
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Retrieves the last update timestamp of the message.
     * 
     * @return The last update timestamp
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Retrieves the ID of the message.
     * 
     * @return The ID of the message
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retrieves the message content.
     * 
     * @return The message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the user associated with the message.
     * 
     * @return The user associated with the message
     */
    public User getUser() {
        return user;
    }

    /**
     * Retrieves the rental associated with the message.
     * 
     * @return The rental associated with the message
     */
    public Rental getRental() {
        return rental;
    }

    /**
     * Builder class for constructing instances of {@link Message}.
     */
    public static class Builder{
        private String message;
        private User user;
        private Rental rental;

        /**
         * Sets the message content for the message being built.
         * 
         * @param message The message content
         * @return The Builder instance
         */
        public Builder setMessage(String message){
            this.message = message;
            return this;
        }

        /**
         * Sets the user associated with the message being built.
         * 
         * @param user The user associated with the message
         * @return The Builder instance
         */
        public Builder setUser(User user){
            this.user = user;
            return this;
        }

        /**
         * Sets the rental associated with the message being built.
         * 
         * @param rental The rental associated with the message
         * @return The Builder instance
         */
        public Builder setRental(Rental rental){
            this.rental = rental;
            return this;
        }

        /**
         * Constructs and returns a new {@link Message} object based on the Builder's current state.
         * 
         * @return A new Message object
         */
        public Message build() {
            Message message = new Message();
                message.message = this.message;
                message.user = this.user;
                message.rental = this.rental;
                return message;
        }
    }
}
