package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.dto.models.AuthenticationRequest;
import com.epam.esm.dto.models.AuthenticationResponse;
import com.epam.esm.dto.validationmarkers.PostValidation;
import com.epam.esm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Class controller for signing up and authentication.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    /**
     * Generates java web token.
     *
     * @param authenticationRequest the {@link AuthenticationRequest} object
     * @return the json object with string token
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) {
        final String jwt = service.authorize(authenticationRequest);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }

    /**
     * Creates new user
     *
     * @param user the user
     * @return the {@link ResponseEntity} object with {@link UserDTO} object, headers and http status
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<UserViewDTO> signUp(@RequestBody @Validated(value = PostValidation.class) UserDTO user) {
        UserViewDTO createdUser = service.create(user);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(UserController.class).slash(user.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        createdUser.add(selfLink);
        return new ResponseEntity<>(createdUser, headers, HttpStatus.OK);
    }
}
