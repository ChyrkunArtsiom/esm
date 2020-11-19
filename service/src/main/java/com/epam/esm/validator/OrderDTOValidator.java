package com.epam.esm.validator;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.OrderHasMissingArgumentException;

public class OrderDTOValidator {
    public static void isOrderValid(OrderDTO dto) {
        if (dto.getUser() == null) {
            throw new OrderHasMissingArgumentException("user");
        }
        if (dto.getCertificates() == null) {
            throw new OrderHasMissingArgumentException("certificates");
        }
    }
}
