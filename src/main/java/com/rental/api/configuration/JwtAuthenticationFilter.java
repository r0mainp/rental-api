package com.rental.api.configuration;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.rental.api.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final HandlerExceptionResolver handlerExceptionResolver;

    private JwtService jwtService;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
        JwtService jwtService,
        UserDetailsService userDetailsService,
        HandlerExceptionResolver handlerExceptionResolver
    ){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    // process each HTTP request
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException{
        final String authHeader = request.getHeader("Authorization"); // get requets header

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response); // pass through filter with no authentication
            return;
        }
        try {
            final String jwt = authHeader.substring(7); // get token
            final String userEmail = jwtService.extractUsername(jwt); // get email

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // if already authenticated

            if (userEmail != null && authentication == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); // load user

                if(jwtService.isTokenValid(jwt, userDetails)){
                    // if token valid create authToken
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken); // setauthToken in context
                }
            }

            filterChain.doFilter(request, response); // pass through filter with authentication
        } catch (Exception exception){
            System.out.println(exception);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }

    }

}
