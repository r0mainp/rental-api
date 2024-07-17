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
    
    private Message(){} // Use builder instead

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Rental getRental() {
        return rental;
    }

    public static class Builder{
        private String message;
        private User user;
        private Rental rental;

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }

        public Builder setUser(User user){
            this.user = user;
            return this;
        }

        public Builder setRental(Rental rental){
            this.rental = rental;
            return this;
        }

        public Message build() {
            Message message = new Message();
                message.message = this.message;
                message.user = this.user;
                message.rental = this.rental;
                return message;
        }
    }
}
