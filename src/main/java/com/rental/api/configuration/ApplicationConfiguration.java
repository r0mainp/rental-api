package com.rental.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rental.api.repository.UserRepository;

@Configuration
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the UserDetailsService implementation that loads user details by email.
     * 
     * @return UserDetailsService implementation based on UserRepository.
     */
    @Bean
    UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Provides a BCrypt password encoder bean.
     * 
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Retrieves the AuthenticationManager bean.
     * 
     * @param config AuthenticationConfiguration instance.
     * @return AuthenticationManager instance.
     * @throws Exception If an error occurs while retrieving the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides an AuthenticationProvider bean using DaoAuthenticationProvider.
     * 
     * @return AuthenticationProvider instance configured with UserDetailsService and password encoder.
     */
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
