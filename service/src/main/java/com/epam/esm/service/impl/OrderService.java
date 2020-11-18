package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link OrderDAO}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = {OrderDAO.class})
public class OrderService implements AbstractService<OrderDTO> {

    private OrderDAO dao;

    /**
     * Sets {@link OrderDAO} object.
     *
     * @param dao the {@link OrderDAO} object
     */
    @Autowired
    public void setDao(OrderDAO dao) {
        this.dao = dao;
    }

    @Override
    public OrderDTO create(OrderDTO entity) {
        throw new UnsupportedOperationException("Order is not supported by create method.");
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO read(int id) {
        Order order = dao.read(id);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDTO update(OrderDTO entity) {
        throw new UnsupportedOperationException("Order is not supported by update method.");
    }

    @Override
    public boolean delete(OrderDTO dto) {
        throw new UnsupportedOperationException("Order is not supported by delete method.");
    }

    @Override
    public List<OrderDTO> readAll() {
        List<OrderDTO> dtos;
        List<Order> orders = dao.readAll();
        dtos = orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    /**
     * Gets the list of {@link OrderDTO} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link OrderDTO} objects
     */
    public List<OrderDTO> readPaginated(Integer page, Integer size) {
        List<OrderDTO> dtos;
        List<Order> entities = dao.readPaginated(page, size);
        dtos = entities.stream().map(OrderMapper::toDto).collect(Collectors.toList());
        return dtos;
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(Integer size) {
        return dao.getLastPage(size);
    }
}
