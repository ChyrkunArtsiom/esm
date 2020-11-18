package com.epam.esm.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Class for Order entity.
 */
@Entity(name = "orders")
@Table(name = "orders", schema = "esm_module2")
public class Order {

    @Id
    @SequenceGenerator(
            name = "orders_id_seq", sequenceName = "esm_module2.certificates_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    private Integer id;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "purchase_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime purchaseDate;

    @ManyToOne
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "certificate_order", schema = "esm_module2",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    private List<GiftCertificate> certificates;

    /**
     * Empty constructor.
     */
    public Order() {

    }

    /**
     * Constructor with all fields.
     *
     * @param cost          the cost
     * @param purchaseDate the purchase date
     * @param user          the user
     * @param certificates  the certificates
     */
    public Order(Integer cost, OffsetDateTime purchaseDate, User user, List<GiftCertificate> certificates) {
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

    public OffsetDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(OffsetDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GiftCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificate> certificates) {
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
        Order order = (Order) obj;
        return cost.equals(order.getCost()) && user.equals(order.getUser())
                && certificates.equals(order.getCertificates());
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String purchaseDate = null;
        if (getPurchaseDate() != null) {
            purchaseDate = getPurchaseDate().format(df);
        }
        return String.format("Order: {id: %d, cost: %d, purchase date: %s, ",
                getId(), getCost(), purchaseDate);
    }
}
