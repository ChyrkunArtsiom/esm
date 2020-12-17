package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAO;
import com.epam.esm.dao.impl.OrderDAO;
import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderViewDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.AbstractService;
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
public class OrderService implements AbstractService<OrderViewDTO, OrderDTO> {

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
    public OrderViewDTO create(OrderDTO dto) {
        Order entity = OrderMapper.toEntity(dto);
        User user = userDAO.read(dto.getUser().getId());
        entity.setUser(user);
        List<GiftCertificate> certificates = dto
                .getCertificates().stream().map(c -> certificateDAO.read(c.getId())).collect(Collectors.toList());
        entity.setCertificates(certificates);
        entity = dao.create(entity);
        return OrderMapper.toOrderViewDTO(entity);
    }

    @Override
    public OrderViewDTO read(int id) {

        Order order = dao.read(id);
        return OrderMapper.toOrderViewDTO(order);
    }

    @Override
    public List<OrderViewDTO> readAll() {
        List<OrderViewDTO> dtos;
        List<Order> orders = dao.readAll();
        dtos = orders.stream().map(OrderMapper::toOrderViewDTO).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<OrderViewDTO> readPaginated(Integer page, Integer size) {
        List<OrderViewDTO> dtos;
        List<Order> entities = dao.readPaginated(page, size);
        dtos = entities.stream().map(OrderMapper::toOrderViewDTO).collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional
    public OrderViewDTO update(OrderDTO dto) {
        Order toUpdate = dao.read(dto.getId());
        if (dto.getUser() != null) {
            User user = userDAO.read(dto.getUser().getId());
            toUpdate.setUser(user);
        }
        if (dto.getCertificates() != null && dto.getCertificates().size() > 0) {
            toUpdate.setCertificates(checkCertificates(dto));
        }
        dao.update(toUpdate);
        return null;
    }

    @Override
    @Transactional
    public boolean delete(OrderDTO dto) {
        Order entity = OrderMapper.toEntity(dto);
        return dao.delete(entity);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return dao.delete(id);
    }

    @Override
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

    private List<GiftCertificate> checkCertificates(OrderDTO order) {
        List<GiftCertificate> newCertificates = new ArrayList<>();
        for (GiftCertificateDTO certificate : order.getCertificates()) {
            GiftCertificate checked = certificateDAO.read(certificate.getId());
            newCertificates.add(checked);
        }
        return newCertificates;
    }

    /**
     * Gets a list of {@link Order} objects by uesr id.
     *
     * @param userId the id of user
     * @return the list of {@link Order} objects
     */
    public List<OrderViewDTO> readOrdersByUserId(int userId) {
        List<OrderViewDTO> dtos;
        List<Order> orders = dao.readOrdersByUserId(userId);
        dtos = orders.stream().map(OrderMapper::toOrderViewDTO).collect(Collectors.toList());
        return dtos;
    }
}
