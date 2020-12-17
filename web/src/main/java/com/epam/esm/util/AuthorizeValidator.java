package com.epam.esm.util;

import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderViewDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.service.impl.OrderService;
import com.epam.esm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The class which checks if user has access to different endpoints.
 */
@Component
public class AuthorizeValidator {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Checks if user has access to list of orders.
     *
     * @param user   the authenticated user
     * @param userId the user id
     * @return {@code true} if user has access
     */
    public boolean hasAccessToOrderList(AuthenticationUser user, int userId) {
        return user.getId().equals(userId);
    }

    /**
     * Checks if user has access to specific order.
     *
     * @param user    the authenticated user
     * @param orderId the order id
     * @return {@code true} if user has access
     */
    public boolean hasAccessToOrder(AuthenticationUser user, int orderId) {
        OrderViewDTO order = orderService.read(orderId);
        if (order != null) {
            return order.getUser().getId().equals(user.getId());
        }
        return false;
    }

    /**
     * Checks if user has access to profile.
     *
     * @param user   the authenticated user
     * @param userId the id of profile
     * @return the boolean
     */
    public boolean hasAccessToProfile(AuthenticationUser user, int userId) {
        UserViewDTO userDTO = userService.read(userId);
        if (userDTO != null) {
            return userDTO.getId().equals(user.getId());
        }
        return false;
    }

    /**
     * Checks if user can create order with specified user.
     *
     * @param user  the user
     * @param order the order to create
     * @return {@code true} if user can create order with specified user
     */
    public boolean canPostOrderWithThisUser(AuthenticationUser user, OrderDTO order) {
        return order.getUser().getId().equals(user.getId());
    }
}
