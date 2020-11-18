package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GetParamIsNotPresent;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class controller for interacting with {@link OrderDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {OrderService.class, EsmExceptionHandler.class})
@RequestMapping("/orders")
@Validated
public class OrderController {

    private OrderService service;

    /**
     * Sets {@link OrderService} object.
     *
     * @param service the {@link OrderService} object
     */
    @Autowired
    public void setService(OrderService service) {
        this.service = service;
    }

    /**
     * Gets {@link OrderDTO} object by id.
     *
     * @param orderId the {@link OrderDTO} object id
     * @return the {@link OrderDTO} object
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<OrderDTO> read(
            @PathVariable @Positive @Digits(integer = 4, fraction = 0) int orderId) {
        OrderDTO order = service.read(orderId);
        Link selfLink = linkTo(OrderController.class).slash(order.getId()).withSelfRel();
        order.add(selfLink);
        buildUserSelfLink(order);
        buildCertificatesSelfLinks(order);
        return order;
    }

    /**
     * Gets all {@link OrderDTO} objects.
     *
     * @return the list of {@link OrderDTO} objects.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel readAll(
            @RequestParam(value = "page", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer page,
            @RequestParam(value = "size", required = false) @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        List<OrderDTO> orders;
        CollectionModel result;
        if (Stream.of(page, size).allMatch(Objects::isNull)) {
            orders = service.readAll();
            orders = buildSelfLinks(orders);
            result = CollectionModel.of(orders);
        } else if (Stream.of(page, size).anyMatch(Objects::isNull)) {
            throw new GetParamIsNotPresent();
        } else {
            int lastPage = service.getLastPage(size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            orders = service.readPaginated(page, size);
            orders = buildSelfLinks(orders);
            result = CollectionModel.of(orders);

            if (hasPrevious(page)) {
                result.add(linkTo(methodOn(OrderController.class).readAll(page - 1, size)).withRel("prev"));
            }
            if (hasNext(page, size)) {
                result.add(linkTo(methodOn(OrderController.class).readAll(page + 1, size)).withRel("next"));
            }
        }
        return result;
    }

    private List<OrderDTO> buildSelfLinks(List<OrderDTO> orders) {
        return orders.stream().peek(order -> {
            order.add(linkTo(OrderController.class).slash(order.getId()).withSelfRel());
            buildUserSelfLink(order);
            buildCertificatesSelfLinks(order);
        }).collect(Collectors.toList());
    }

    private void buildUserSelfLink(OrderDTO order) {
        order.getUser().add(linkTo(UserController.class).slash(order.getUser().getId()).withSelfRel());
    }

    private void buildCertificatesSelfLinks(OrderDTO order) {
        List<GiftCertificateDTO> certificates = order.getCertificates().stream()
                .peek(c -> {
                    c.add(linkTo(GiftCertificateController.class).slash(c.getId()).withSelfRel());
                    buildTagsSelfLink(c);
                }).collect(Collectors.toList());
        order.setCertificates(certificates);
    }

    private void buildTagsSelfLink(GiftCertificateDTO certificate) {
        if (certificate.getTags() != null) {
            Set<TagDTO> tags = certificate.getTags().stream().map(t ->
                    t.add(linkTo(TagController.class).slash(t.getId()).withSelfRel()))
                    .collect(Collectors.toSet());
            certificate.setTags(tags);
        }
    }

    private boolean hasNext(int page, int size) {
        int lastPage = service.getLastPage(size);
        return page < lastPage;
    }

    private boolean hasPrevious(int page) {
        return page > 1;
    }
}
