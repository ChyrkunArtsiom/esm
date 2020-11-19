package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that converts {@link Order} and {@link OrderDTO} objects to each other.
 */
public class OrderMapper {

    /**
     * Converts {@link OrderDTO} object to a {@link Order}.
     *
     * @param dto the {@link OrderDTO} object
     * @return the {@link Order} object
     */
    public static Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setCost(dto.getCost());
        User user = UserMapper.toEntity(dto.getUser());
        order.setUser(user);
        List<GiftCertificate> certificates = new ArrayList<>();
        for (GiftCertificateDTO certificate : dto.getCertificates()) {
            certificates.add(GiftCertificateMapper.toEntity(certificate));
        }
        order.setCertificates(certificates);
        return order;
    }

    /**
     * Converts {@link Order} object to a {@link OrderDTO}.
     *
     * @param entity the {@link Order} object
     * @return the {@link OrderDTO} object
     */
    public static OrderDTO toDto(Order entity) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setCost(entity.getCost());
        dto.setUser(UserMapper.toDto(entity.getUser()));
        if (entity.getPurchaseDate() != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            dto.setPurchaseDate(entity.getPurchaseDate().format(df));
        }
        List<GiftCertificateDTO> certificates = new ArrayList<>();
        if (entity.getCertificates() != null) {
            for (GiftCertificate certificate : entity.getCertificates()) {
                certificates.add(GiftCertificateMapper.toDto(certificate));
            }
        }
        dto.setCertificates(certificates);
        return dto;
    }
}
