package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.OrderPostValidation;
import com.epam.esm.dto.validationmarkers.OrderPutValidation;
import com.epam.esm.exception.PageParamIsNotPresent;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.EsmExceptionHandler;
import com.epam.esm.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Creates {@link OrderDTO} object. Returns location and status.
     *
     * @param dto the {@link OrderDTO} object.
     * @return the {@link ResponseEntity} object with {@link OrderDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<OrderDTO> createOrder(@Validated(value = OrderPostValidation.class) @RequestBody OrderDTO dto) {
        OrderDTO createdOrder = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(OrderController.class).slash(createdOrder.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        createdOrder.add(selfLink);
        buildUserSelfLink(createdOrder);
        buildCertificatesSelfLinks(createdOrder);
        return new ResponseEntity<>(createdOrder, headers, HttpStatus.OK);
    }

    /**
     * Gets {@link OrderDTO} object by id.
     *
     * @param orderId the {@link OrderDTO} object id
     * @return the {@link OrderDTO} object
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<OrderDTO> readOrder(
            @PathVariable @Positive @Digits(integer = 10, fraction = 0) int orderId) {
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
    public CollectionModel readAllOrders(
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
            throw new PageParamIsNotPresent();
        } else {
            int lastPage = service.getLastPage(size);
            if (page > lastPage) {
                throw new ResourceNotFoundException();
            }
            orders = service.readPaginated(page, size);
            orders = buildSelfLinks(orders);
            result = CollectionModel.of(orders);

            if (hasPrevious(page)) {
                result.add(linkTo(methodOn(OrderController.class).readAllOrders(page - 1, size)).withRel("prev"));
            }
            if (hasNext(page, size)) {
                result.add(linkTo(methodOn(OrderController.class).readAllOrders(page + 1, size)).withRel("next"));
            }
        }
        return result;
    }

    /**
     * Updates {@link OrderDTO} object.
     *
     * @param dto the {@link OrderDTO} object to update.
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<?> updateOrder(@RequestBody @Validated(value = OrderPutValidation.class) OrderDTO dto) {
        OrderDTO created = service.update(dto);
        if (created != null) {
            HttpHeaders headers = new HttpHeaders();
            Link selfLink = linkTo(OrderController.class).slash(created.getId()).withSelfRel();
            headers.setLocation(selfLink.toUri());
            created.add(selfLink);
            buildUserSelfLink(created);
            buildCertificatesSelfLinks(created);
            return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Deletes {@link OrderDTO} object.
     *
     * @param dto the {@link OrderDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<?> deleteOrder(@RequestBody @Validated(value = DeleteValidation.class) OrderDTO dto) {
        if (service.delete(dto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes {@link OrderDTO} object.
     *
     * @param tagId the id of {@link OrderDTO} object to delete
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(value = "/{tagId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrderByUrlId(@PathVariable @Positive @Digits(integer = 10, fraction = 0) int tagId) {
        if (service.delete(tagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets a list of most frequent tags from a user, who has the most expensive amount of orders.
     *
     * @return the list of {@link TagDTO} objects
     */
    @RequestMapping(value = "/most_frequent_tag", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel getMostFrequentTags() {
        List<TagDTO> tags;
        CollectionModel result;
            tags = service.getMostFrequentTags();
            tags = tags.stream()
                    .peek(t -> t
                            .add(linkTo(TagController.class)
                                    .slash(t.getId())
                                    .withSelfRel()))
                    .collect(Collectors.toList());
            result = CollectionModel.of(tags);
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
