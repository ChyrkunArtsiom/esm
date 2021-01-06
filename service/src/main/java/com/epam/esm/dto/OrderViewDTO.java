package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
import java.util.Objects;

@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderViewDTO extends RepresentationModel<OrderViewDTO> {
    private Integer id;

    private Double cost;

    private String purchaseDate;

    private UserViewDTO user;

    private List<GiftCertificateDTO> certificates;

    /**
     * Empty constructor.
     */
    public OrderViewDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id           the id
     * @param cost         the cost
     * @param purchaseDate the purchase date
     * @param user         the {@link UserViewDTO} object
     * @param certificates the list of {@link GiftCertificateDTO} objects
     */
    public OrderViewDTO(Integer id, Double cost, String purchaseDate, UserViewDTO user, List<GiftCertificateDTO> certificates) {
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

    public UserViewDTO getUser() {
        return user;
    }

    public void setUser(UserViewDTO user) {
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
        OrderViewDTO order = (OrderViewDTO) obj;
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
