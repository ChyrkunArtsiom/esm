package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.GetParamIsNotPresent;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@ComponentScan(basePackageClasses = {UserService.class, EsmExceptionHandler.class})
@RequestMapping("/users")
public class UserController {

    private UserService service;

    /**
     * Sets {@link UserService} object.
     *
     * @param service the {@link UserService} object
     */
    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    /**
     * Gets {@link UserDTO} object by id.
     *
     * @param userId the {@link UserDTO} object id
     * @return the {@link UserDTO} object
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<UserDTO> readUser(@PathVariable @Positive @Digits(integer = 4, fraction = 0) int userId) {
        UserDTO user = service.read(userId);
        Link selfLink = linkTo(TagController.class).slash(user.getId()).withSelfRel();
        user.add(selfLink);
        return user;
    }

    /**
     * Gets all {@link UserDTO} objects.
     *
     * @return the list of {@link UserDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readAllUsers(
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        List<UserDTO> users;
        CollectionModel result;
        if (Stream.of(page, size).allMatch(Objects::isNull)) {
            users = service.readAll();
            users = buildSelfLinks(users);
            result = CollectionModel.of(users);
        } else if (Stream.of(page, size).anyMatch(Objects::isNull)) {
            throw new GetParamIsNotPresent();
        } else {
            int lastPage = service.getLastPage(size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            users = service.readPaginated(page, size);
            users = buildSelfLinks(users);
            result = CollectionModel.of(users);

            if (hasPrevious(page)) {
                result.add(linkTo(methodOn(UserController.class).readAllUsers(page - 1, size)).withRel("prev"));
            }
            if (hasNext(page, size)) {
                result.add(linkTo(methodOn(UserController.class).readAllUsers(page + 1, size)).withRel("next"));
            }
        }
        return result;
    }

    private List<UserDTO> buildSelfLinks(List<UserDTO> tags) {
        return tags.stream()
                .map(t -> t
                        .add(linkTo(UserController.class).slash(t.getId()).withSelfRel()))
                .collect(Collectors.toList());
    }

    private boolean hasNext(int page, int size) {
        int lastPage = service.getLastPage(size);
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }
}
