package com.epam.esm.controller.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class GiftCertificateDTO extends EntityDTO {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Integer duration;

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
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
        GiftCertificateDTO certificate = (GiftCertificateDTO) obj;
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
