package com.rental.api.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * Represents a user entity in the application.
 * <p>
 * This class encapsulates user details such as name, email, password, and associated rentals.
 * It implements Spring Security's UserDetails interface to integrate with authentication mechanisms.
 * </p>
 */
@Table(name="users")
@Entity
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rental> rentals = new HashSet<>();

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
    private User() {}

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's identifier
     */
    public Integer getId() {
        return id;
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
     * Retrieves the email address of the user.
     *
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the creation timestamp of the user.
     *
     * @return The timestamp when the user was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Retrieves the last update timestamp of the user.
     *
     * @return The timestamp when the user was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Retrieves the authorities granted to the user.
     * <p>
     * This method is required by the UserDetails interface.
     * </p>
     *
     * @return The list of authorities granted to the user (empty list in this implementation)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Retrieves the username used to authenticate the user.
     * <p>
     * This method is required by the UserDetails interface.
     * </p>
     *
     * @return The username (email) used to authenticate the user
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Builder pattern for creating instances of User.
     * <p>
     * This builder allows constructing instances of User with required attributes.
     * </p>
     */
    public static class Builder {
        private String name;
        private String email;
        private String password;

        /**
         * Sets the name for the user being built.
         *
         * @param name The name of the user
         * @return The builder instance for method chaining
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the email address for the user being built.
         *
         * @param email The email address of the user
         * @return The builder instance for method chaining
         */
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the password for the user being built.
         *
         * @param password The password of the user
         * @return The builder instance for method chaining
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Constructs a new User object with the set attributes.
         *
         * @return The constructed User object
         */
        public User build() {
            User user = new User();
            user.name = this.name;
            user.email = this.email;
            user.password = this.password;
            return user;
        }

    }

    // TODO Usefull ?

    /**
     * Indicates whether the user's account has not expired.
     * <p>
     * This method always returns true, indicating that the account never expires.
     * </p>
     *
     * @return Always returns {@code true} indicating the account never expires
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is not locked.
     * <p>
     * This method always returns true, indicating that the account is never locked.
     * </p>
     *
     * @return Always returns {@code true} indicating the account is never locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are not expired.
     * <p>
     * This method always returns true, indicating that the credentials never expire.
     * </p>
     *
     * @return Always returns {@code true} indicating the credentials never expire
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * <p>
     * This method always returns true, indicating that the user account is always enabled.
     * </p>
     *
     * @return Always returns {@code true} indicating the account is always enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
