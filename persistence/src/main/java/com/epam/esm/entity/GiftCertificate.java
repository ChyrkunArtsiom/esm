package com.epam.esm.entity;

import java.time.OffsetDateTime;
import java.util.Objects;


public class GiftCertificate extends AbstractEntity {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private OffsetDateTime createDate;
    private OffsetDateTime lastUpdateDate;
    private Integer duration;

    public GiftCertificate() {
    }

    public GiftCertificate(Integer id, String name, String description, Double price,
                           OffsetDateTime createDate, OffsetDateTime lastUpdateDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public OffsetDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(OffsetDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GiftCertificate certificate = (GiftCertificate) obj;
        return id.equals(certificate.id) && name.equals(certificate.name) && description.equals(certificate.description)
                && price.equals(certificate.price) && createDate.equals(certificate.createDate)
                && lastUpdateDate.equals(certificate.lastUpdateDate) && duration.equals(certificate.duration);
    }

    @Override
    public String toString() {
        return "GiftCertificate: {id: " + id + ", name: " + name + ", description: " + description + ", price: " + price +
                ", created: " + createDate + ", updated: " + lastUpdateDate + ", duration: " + duration + "}";
    }
}
