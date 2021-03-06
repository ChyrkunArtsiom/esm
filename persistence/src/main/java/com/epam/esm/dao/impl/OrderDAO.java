package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoOrderException;
import com.epam.esm.exception.OrderHasDuplicateCertificatesException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class OrderDAO implements AbstractDAO<Order> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order entity) {
        entityManager.persist(entity);
        try {
            entityManager.flush();
        } catch (PersistenceException ex) {
            throw new OrderHasDuplicateCertificatesException("Order has duplicate certificates.");
        }
        return entity;
    }

    @Override
    public Order read(int id) {
        Order order = entityManager.find(Order.class, id);
        if (order == null) {
            throw new NoOrderException(
                    String.format("Order with id = {%s} doesn't exist.", String.valueOf(id)),
                    String.valueOf(id));
        } else {
            return order;
        }
    }

    @Override
    public Order read(String name) {
        throw new UnsupportedOperationException("Order is not supported by read by name method.");
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

    @Override
    public Order update(Order entity) {
        Order updatedOrder = entityManager.merge(entity);
        entityManager.flush();
        return updatedOrder;
    }

    @Override
    public boolean delete(Order entity) {
        try {
            TypedQuery<Order> query = entityManager.createQuery(
                    "SELECT o FROM orders o WHERE o.id=:id", Order.class);
            query.setParameter("id", entity.getId());
            Order toDelete = query.getSingleResult();
            entityManager.remove(toDelete);
            return true;
        } catch (NoResultException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            Order order = read(id);
            entityManager.remove(order);
            return true;
        } catch (NoResultException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Gets a list of most frequent tags from a user, who has the most expensive amount of orders.
     *
     * @return the list of {@link Tag} objects
     */
    public List<Tag> getMostFrequentTags() {
        Query query = entityManager.createNativeQuery(
                "SELECT id, name FROM esm_module2.get_most_popular_tags_for_richest_client()", Tag.class);
        return (List<Tag>)query.getResultList();
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

    /**
     * Gets a list of {@link Order} objects by uesr id.
     *
     * @param userId the id of user
     * @param page the page number
     * @param size the size
     * @return the list of {@link Order} objects
     */
    public List<Order> readOrdersByUserId(int userId, Integer page, Integer size) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM orders o WHERE o.user.id =:user_id ORDER BY o.id", Order.class);
        query.setParameter("user_id", userId);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    /**
     * Gets a number of last page of objects for a specific user.
     *
     * @param userId the id of user
     * @param size the size
     * @return the number of last page
     */
    public int getLastPageForUser(int userId, Integer size) {
        Query query = entityManager.createQuery("SELECT count(o) FROM orders o WHERE o.user.id =:user_id");
        query.setParameter("user_id", userId);
        Long count = (Long)query.getSingleResult();
        int pages = count.intValue()/size;
        if (count % size > 0) {
            pages++;
        }
        return pages;
    }
}
