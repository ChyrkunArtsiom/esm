package com.epam.esm.dto;

import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.OrderPostValidation;
import com.epam.esm.dto.validationmarkers.OrderPutValidation;
import com.epam.esm.validator.ValidationMessageManager;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.Order}.
 */
public class OrderDTO extends RepresentationModel<OrderDTO> {
    @NotNull(groups = {OrderPutValidation.class, DeleteValidation.class}, message = ValidationMessageManager.ID_INVALID)
    @Digits(integer = 10, fraction = 2, message = ValidationMessageManager.ID_INVALID,
            groups = {OrderPutValidation.class, DeleteValidation.class})
    @Positive(message = ValidationMessageManager.ID_INVALID,
            groups = {OrderPutValidation.class, DeleteValidation.class})
    private Integer id;

    private Double cost;

    private String purchaseDate;

    @NotNull(message = ValidationMessageManager.ORDER_BLANK_USER, groups = OrderPostValidation.class)
    @Valid
    private UserDTO user;

    @NotNull(message = ValidationMessageManager.ORDER_BLANK_OR_EMPTY_CERTIFICATES, groups = OrderPostValidation.class)
    @NotEmpty(message = ValidationMessageManager.ORDER_BLANK_OR_EMPTY_CERTIFICATES, groups = OrderPostValidation.class)
    @Valid
    private List<GiftCertificateDTO> certificates;

    /**
     * Empty constructor.
     */
    public OrderDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id           the id
     * @param cost         the cost
     * @param purchaseDate the purchase date
     * @param user         the {@link UserDTO} object
     * @param certificates the list of {@link GiftCertificateDTO} objects
     */
    public OrderDTO(Integer id, Double cost, String purchaseDate, UserDTO user, List<GiftCertificateDTO> certificates) {
        this.id = id;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.user = user;
        this.certificates = certificates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<GiftCertificateDTO> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificateDTO> certificates) {
        this.certificates = certificates;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OrderDTO order = (OrderDTO) obj;
        return cost.equals(order.getCost()) && user.equals(order.getUser())
                && certificates.equals(order.getCertificates());
    }

    @Override
    public String toString() {
        StringBuilder stringOfOrder = new StringBuilder(String.format("Order: {id: %d, cost: %f, purchase date: %s, ",
                getId(), getCost(), purchaseDate));
        stringOfOrder.append(String.format("user: %s, ", getUser()));
        stringOfOrder.append("certificates: ");
        for (int i = 0; i < getCertificates().size(); i++) {
            stringOfOrder.append(getCertificates().get(i));
            if (i == (getCertificates().size() - 1)) {
                stringOfOrder.append(".");
            } else {
                stringOfOrder.append(", ");
            }
        }
        return stringOfOrder.toString();
    }
}
