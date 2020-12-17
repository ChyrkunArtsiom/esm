package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.service.impl.UserService;
import com.epam.esm.util.AuthorizeValidator;
import com.epam.esm.util.linkbuilders.UserLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

@RestController
@ComponentScan
@RequestMapping("/users")
@Validated
public class UserController extends AbstractController<UserService, UserLinkBuilder, UserDTO> {

    private AuthorizeValidator authorizeValidator;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setAuthorizeValidator(AuthorizeValidator authorizeValidator) {
        this.authorizeValidator = authorizeValidator;
    }

    @Autowired
    public void setLinkBuilder(UserLinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    /**
     * Gets {@link UserDTO} object by id.
     *
     * @param userId the {@link UserDTO} object id
     * @return the {@link UserDTO} object
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or @authorizeValidator.hasAccessToProfile(#authUser, #userId)")
    public RepresentationModel<UserViewDTO> readUser(@PathVariable @Positive @Digits(integer = 10, fraction = 0) int userId,
                                                     @AuthenticationPrincipal AuthenticationUser authUser) {
        UserViewDTO user = service.read(userId);
        linkBuilder.buildLink(user);
        return user;
    }

    /**
     * Gets all {@link UserDTO} objects.
     *
     * @return the list of {@link UserDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readAllUsers(
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        return readPaginatedForController(page, size);
    }
}
