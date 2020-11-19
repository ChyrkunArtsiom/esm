package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dao.impl.OrderDAO;
import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ArgumentIsNotPresent;
import com.epam.esm.exception.NoOrderException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.validator.OrderDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link OrderDAO}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = {OrderDAO.class})
public class OrderService implements AbstractService<OrderDTO> {

    private OrderDAO dao;

    private UserDAO userDAO;

    private GiftCertificateDAO certificateDAO;

    /**
     * Sets {@link OrderDAO} object.
     *
     * @param dao the {@link OrderDAO} object
     */
    @Autowired
    public void setDao(OrderDAO dao) {
        this.dao = dao;
    }

    /**
     * Sets {@link UserDAO} object.
     *
     * @param userDAO the {@link UserDAO} object
     */
    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Sets {@link GiftCertificateDAO} object.
     *
     * @param certificateDAO the {@link GiftCertificateDAO} object
     */
    @Autowired
    public void setCertificateDAO(GiftCertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO dto) {
        OrderDTOValidator.isOrderValid(dto);
        Order entity = OrderMapper.toEntity(dto);
        User user = userDAO.read(dto.getUser().getId());
        entity.setUser(user);
        List<GiftCertificate> certificates = dto
                .getCertificates().stream().map(c -> certificateDAO.read(c.getId())).collect(Collectors.toList());
        entity.setCertificates(certificates);
        entity = dao.create(entity);
        return OrderMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO read(int id) {
        Order order = dao.read(id);
        return OrderMapper.toDto(order);
    }

    @Override
    public OrderDTO update(OrderDTO dto) {
        if (dto.getId() != null) {
            Order toUpdate = dao.read(dto.getId());
            Order substitute = OrderMapper.toEntity(dto);
            try {
                if (dto.getUser() != null) {
                    User user = userDAO.read(dto.getUser().getId());
                    toUpdate.setUser(substitute.getUser());
                }
                if (dto.getCertificates() != null) {
                    toUpdate.setCertificates(checkCertificates(substitute));
                }
                dao.update(toUpdate);
                return null;
            } catch (NoOrderException ex) {
                return create(dto);
            }
        } else {
            throw new ArgumentIsNotPresent("Order id is not presented", "id");
        }
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

    /**
     * Gets a list of most frequent tags from a user, who has the most expensive amount of orders.
     *
     * @return the list of {@link TagDTO} objects
     */
    public List<TagDTO> getMostFrequentTags() {
        List<Tag> entities = dao.getMostFrequentTags();
        return entities.stream().map(TagMapper::toDto).collect(Collectors.toList());
    }

    private List<GiftCertificate> checkCertificates(Order order) {
        List<GiftCertificate> newCertificates = new ArrayList<>();
        for (GiftCertificate certificate : order.getCertificates()) {
            GiftCertificate checked = certificateDAO.read(certificate.getId());
            newCertificates.add(checked);
        }
        return newCertificates;
    }
}
