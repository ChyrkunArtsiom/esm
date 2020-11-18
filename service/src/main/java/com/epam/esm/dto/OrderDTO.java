package com.epam.esm.dto;

import com.epam.esm.entity.Order;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.Order}.
 */
@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private Integer id;

    private Integer cost;

    private String purchaseDate;

    private UserDTO user;

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
    public OrderDTO(Integer id, Integer cost, String purchaseDate, UserDTO user, List<GiftCertificateDTO> certificates) {
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
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
        return String.format("Order: {id: %d, cost: %d, purchase date: %s, ",
                getId(), getCost(), purchaseDate);
    }
}
