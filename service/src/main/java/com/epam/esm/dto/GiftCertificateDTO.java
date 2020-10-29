package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

public class GiftCertificateDTO extends AbstractDTO {
    private String description;
    private Double price;
    private String createDate;
    private String lastUpdateDate;
    private Integer duration;
    private List<String> tags;

    public GiftCertificateDTO() {
    }

    public GiftCertificateDTO(Integer id, String name, String description, Double price, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public GiftCertificateDTO(Integer id, String name, String description, Double price, String createDate,
                              String lastUpdateDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
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
