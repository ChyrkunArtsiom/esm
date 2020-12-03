package com.epam.esm.dto.models;

/**
 * The response class which holds authentication token.
 */
public class AuthenticationResponse {

    private String jwt;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
