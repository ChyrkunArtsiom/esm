package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderViewDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.OrderPostValidation;
import com.epam.esm.dto.validationmarkers.OrderPutValidation;
import com.epam.esm.service.impl.OrderService;
import com.epam.esm.util.AuthorizeValidator;
import com.epam.esm.util.linkbuilders.OrderLinkBuilder;
import com.epam.esm.util.linkbuilders.TagLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Class controller for interacting with {@link OrderDTO} objects.
 */
@RestController
@ComponentScan(basePackageClasses = {OrderService.class})
@RequestMapping("/orders")
@Validated
public class OrderController extends AbstractController<OrderService, OrderLinkBuilder, OrderDTO> {

    private AuthorizeValidator authorizeValidator;

    private TagLinkBuilder tagLinkBuilder;

    @Override
    @Autowired
    public void setService(OrderService service) {
        super.setService(service);
    }

    @Autowired
    public void setAuthorizeValidator(AuthorizeValidator authorizeValidator) {
        this.authorizeValidator = authorizeValidator;
    }

    @Override
    @Autowired
    public void setLinkBuilder(OrderLinkBuilder linkBuilder) {
        super.setLinkBuilder(linkBuilder);
    }

    @Autowired
    public void setTagLinkBuilder(TagLinkBuilder tagLinkBuilder) {
        this.tagLinkBuilder = tagLinkBuilder;
    }

    /**
     * Creates {@link OrderDTO} object. Returns location and status.
     *
     * @param dto the {@link OrderDTO} object.
     * @return the {@link ResponseEntity} object with {@link OrderDTO} object, headers and http status
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/hal+json")
    @PreAuthorize("hasRole('ADMIN') or @authorizeValidator.canPostOrderWithThisUser(#user, #dto)")
    public ResponseEntity<OrderViewDTO> createOrder(
            @Validated(value = OrderPostValidation.class) @RequestBody OrderDTO dto,
            @AuthenticationPrincipal AuthenticationUser user) {
        OrderViewDTO createdOrder = service.create(dto);
        HttpHeaders headers = new HttpHeaders();
        Link selfLink = linkTo(OrderController.class).slash(createdOrder.getId()).withSelfRel();
        headers.setLocation(selfLink.toUri());
        linkBuilder.buildLink(createdOrder);
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
    @PreAuthorize("hasRole('ADMIN') or @authorizeValidator.hasAccessToOrder(#user, #orderId)")
    public RepresentationModel<OrderViewDTO> readOrder(
            @PathVariable @Positive @Digits(integer = 10, fraction = 0) int orderId,
            @AuthenticationPrincipal AuthenticationUser user) {
        OrderViewDTO order = service.read(orderId);
        linkBuilder.buildLink(order);
        return order;
    }

    @RequestMapping(value = "/{userId}/order", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or @authorizeValidator.hasAccessToOrderList(#user, #userId)")
    public CollectionModel readOrdersByUserId(
            @PathVariable @Positive @Digits(integer = 10, fraction = 0) int userId,
            @AuthenticationPrincipal AuthenticationUser user) {
        List<OrderViewDTO> orders;
        CollectionModel result;
        orders = service.readOrdersByUserId(userId);
        linkBuilder.buildLinks(orders);
        result = CollectionModel.of(orders);
        return result;
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
            @RequestParam(value = "size", required = false, defaultValue = "5") @Positive @Digits(integer = 4, fraction = 0) Integer size
    ) {
        return readPaginatedForController(page, size);
    }

    /**
     * Updates {@link OrderDTO} object.
     *
     * @param dto the {@link OrderDTO} object to update.
     * @return the {@link ResponseEntity} object with http status
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<OrderViewDTO> updateOrder(@RequestBody @Validated(value = OrderPutValidation.class) OrderDTO dto) {
        OrderViewDTO created = service.update(dto);
        if (created != null) {
            HttpHeaders headers = new HttpHeaders();
            Link selfLink = linkTo(OrderController.class).slash(created.getId()).withSelfRel();
            headers.setLocation(selfLink.toUri());
            linkBuilder.buildLink(created);
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
            tagLinkBuilder.buildLinks(tags);
            result = CollectionModel.of(tags);
        return result;
    }
}
