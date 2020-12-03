package com.epam.esm.util.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

/**
 * The interface for working with jawa web tokens.
 */
public interface JwtUtil {
    /**
     * Gets username from token.
     *
     * @param token the token
     * @return the username from token
     */
    String getUsernameFromToken(String token);

    /**
     * Gets user id from token.
     *
     * @param token the token
     * @return the user id from token
     */
    Integer getUserIdFromToken(String token);

    /**
     * Gets expiration date from token.
     *
     * @param token the token
     * @return the expiration date from token
     */
    Date getExpirationDateFromToken(String token);

    /**
     * Checks if token is expired.
     *
     * @param token the token
     * @return {@code true} if token is expired
     */
    Boolean isTokenExpired(String token);

    /**
     * Gets claims from token.
     *
     * @param token the token
     * @return the claims from token
     */
    Claims getClaimsFromToken(String token);

    /**
     * Generates token.
     *
     * @param userDetails the user details
     * @return the string
     */
    String generateToken(UserDetails userDetails);

    /**
     * Validates token.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return {@code true} if token is valid
     */
    Boolean validateToken(String token, UserDetails userDetails);

    /**
     * Extract claim.
     *
     * @param <T>            the type of claim
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @return the {@link T} object
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
