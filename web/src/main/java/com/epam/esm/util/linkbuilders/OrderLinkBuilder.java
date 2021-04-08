package com.epam.esm.util.linkbuilders;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The link builder class for {@link OrderDTO} objects.
 */
@Component
public class OrderLinkBuilder implements LinkBuilder<OrderViewDTO> {

    private GiftCertificateLinkBuilder certificateLinkBuilder;
    private UserLinkBuilder userLinkBuilder;

    @Autowired
    public void setCertificateLinkBuilder(GiftCertificateLinkBuilder certificateLinkBuilder) {
        this.certificateLinkBuilder = certificateLinkBuilder;
    }

    @Autowired
    public void setUserLinkBuilder(UserLinkBuilder userLinkBuilder) {
        this.userLinkBuilder = userLinkBuilder;
    }

    @Override
    public void buildLink(OrderViewDTO target) {
        Link selfLink = linkTo(OrderController.class).slash(target.getId()).withSelfRel();
        userLinkBuilder.buildLink(target.getUser());
        certificateLinkBuilder.buildLinks(target.getCertificates());
        target.add(selfLink);
    }

    @Override
    public void buildLinks(Collection<OrderViewDTO> targets) {
        targets.forEach(this::buildLink);
    }

    @Override
    public void buildPreviousPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(OrderController.class).readAllOrders(page - 1, size)).withRel("prev"));
    }

    @Override
    public void buildNextPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(OrderController.class).readAllOrders(page + 1, size)).withRel("next"));
    }

    @Override
    public void buildLastPageLink(CollectionModel target, int lastPage, int size) {
        target.add(linkTo(methodOn(OrderController.class).readAllOrders(lastPage, size)).withRel("last"));
    }

    public void buildPreviousPageLinkForUser(CollectionModel target, int userId, int page, int size,
                                             AuthenticationUser user) {
        target.add(linkTo(methodOn(OrderController.class)
                .readOrdersByUserId(userId, page - 1, size, user)).withRel("prev"));
    }

    public void buildNextPageLinkForUser(CollectionModel target, int userId, int page, int size,
                                         AuthenticationUser user) {
        target.add(linkTo(methodOn(OrderController.class)
                .readOrdersByUserId(userId, page + 1, size, user)).withRel("next"));
    }

    public void buildLastPageLinkForUser(CollectionModel target, int userId, int lastPage, int size,
                                         AuthenticationUser user) {
        target.add(linkTo(methodOn(OrderController.class)
                .readOrdersByUserId(userId, lastPage, size, user)).withRel("last"));
    }
}
