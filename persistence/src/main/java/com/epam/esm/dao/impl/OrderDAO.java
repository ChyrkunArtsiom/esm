package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ErrorCodesManager;
import com.epam.esm.exception.NoOrderException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Order.class)
public class OrderDAO implements AbstractDAO<Order> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order entity) {
        return null;
    }

    @Override
    public Order read(int id) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new NoOrderException(
                    String.format("Order with id = {%s} doesn't exist.", String.valueOf(id)),
                    String.valueOf(id), ErrorCodesManager.ORDER_DOESNT_EXIST);
        } else {
            return order;
        }
    }

    @Override
    public Order read(String name) {
        throw new UnsupportedOperationException("Order is not supported by read by name method.");
    }

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public boolean delete(Order entity) {
        return false;
    }

    @Override
    public List<Order> readAll() {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM orders o ORDER BY o.id", Order.class);
        return query.getResultList();
    }

    /**
     * Gets the list of {@link Order} objects by page and size.
     *
     * @param page the page number
     * @param size the size
     * @return the list of {@link Order} objects
     */
    public List<Order> readPaginated(Integer page, Integer size) {
        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM orders o ORDER BY o.id", Order.class);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    /**
     * Gets a number of last page of objects.
     *
     * @param size the size of page
     * @return the number of last page
     */
    public int getLastPage(Integer size) {
        Query query = entityManager.createQuery("SELECT count(o.id) FROM orders o");
        Long count = (Long)query.getSingleResult();
        int pages = count.intValue()/size;
        if (count % size > 0) {
            pages++;
        }
        return pages;
    }
}
