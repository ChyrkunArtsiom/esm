package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    private static OrderDTO dto;
    private static Order entity;

    @BeforeAll
    static void setUp() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        OffsetDateTime date = OffsetDateTime.parse(OffsetDateTime.now().format(df));
        User userEntity = new User(1, "user", "password".toCharArray());
        UserDTO userDTO = UserMapper.toDto(userEntity);
        GiftCertificate certificate = new GiftCertificate(
                "certificate", "description", 100.0, date, null, 10, null);
        GiftCertificateDTO certificateDTO = GiftCertificateMapper.toDto(certificate);
        dto = new OrderDTO(1, 10, date.toString(), userDTO, Arrays.asList(certificateDTO, certificateDTO));
        entity = new Order(10, date, userEntity, Arrays.asList(certificate, certificate));

    }

    @Test
    public void toEntity() {
        Order converted = OrderMapper.toEntity(dto);
        assertEquals(entity, converted);
    }

    @Test
    public void toDto() {
        OrderDTO converted = OrderMapper.toDto(entity);
        assertEquals(dto, converted);
    }
}