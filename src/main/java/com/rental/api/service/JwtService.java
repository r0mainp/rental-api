package com.rental.api.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username (subject) extracted from the JWT token.
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using a provided resolver function.
     *
     * @param token The JWT token from which to extract the claim.
     * @param claimResolver The function to resolve the desired claim from the token's claims.
     * @param <T> The type of the claim to extract.
     * @return The resolved claim extracted from the JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    
    /**
     * Generates a JWT token for a user.
     *
     * @param userDetails The details of the user for whom the token is generated.
     * @return The generated JWT token as a string.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for a user with additional claims.
     *
     * @param extraClaims Additional claims to include in the JWT token.
     * @param userDetails The details of the user for whom the token is generated.
     * @return The generated JWT token as a string.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Validates whether a JWT token is valid for the specified user.
     *
     * @param token The JWT token to validate.
     * @param userDetails The details of the user against which to validate the token.
     * @return true if the token is valid for the user, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Retrieves the expiration time of JWT tokens.
     *
     * @return The expiration time of JWT tokens in milliseconds.
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    
    /**
     * Builds a JWT token with specified claims and expiration time.
     *
     * @param extraClaims Additional claims to include in the JWT token.
     * @param userDetails The details of the user for whom the token is generated.
     * @param expiration The expiration time of the JWT token in milliseconds.
     * @return The built JWT token as a string.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token to check for expiration.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the JWT token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token The JWT token from which to extract all claims.
     * @return All claims extracted from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decodes the secret key and generates the signing key used to sign JWT tokens.
     *
     * @return The generated signing key for JWT tokens.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
