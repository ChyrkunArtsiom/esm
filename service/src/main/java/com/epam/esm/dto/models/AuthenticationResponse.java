package com.epam.esm.dto.models;

import com.epam.esm.dto.UserViewDTO;

import java.util.Collections;
import java.util.Set;

/**
 * The response class which holds authentication token.
 */
public class AuthenticationResponse {

    private String jwt;

    private UserViewDTO user;

    private Set<String> roles;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt, UserViewDTO user, Set<String> roles) {
        this.jwt = jwt;
        this.user = new UserViewDTO(user.getId(), user.getName(), user.getFirstName(),
                user.getSecondName(), user.getBirthday());
        this.roles = Collections.unmodifiableSet(roles);
    }

    public String getJwt() {
        return jwt;
    }

    public UserViewDTO getUser() {
        return user;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
