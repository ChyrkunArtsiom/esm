package com.epam.esm.util.linkbuilders;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The link builder class for {@link UserDTO} objects.
 */
@Component
public class UserLinkBuilder implements LinkBuilder<UserViewDTO> {
    @Override
    public void buildLink(UserViewDTO target) {
        Link selfLink = linkTo(UserController.class).slash(target.getId()).withSelfRel();
        target.add(selfLink);
    }

    @Override
    public void buildLinks(Collection<UserViewDTO> targets) {
        targets.forEach(this::buildLink);
    }

    @Override
    public void buildPreviousPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(UserController.class).readAllUsers(page - 1, size)).withRel("prev"));
    }

    @Override
    public void buildNextPageLink(CollectionModel target, int page, int size) {

    }

    @Override
    public void buildLastPageLink(CollectionModel target, int lastPage, int size) {
        target.add(linkTo(methodOn(UserController.class).readAllUsers(lastPage, size)).withRel("last"));
    }
}
