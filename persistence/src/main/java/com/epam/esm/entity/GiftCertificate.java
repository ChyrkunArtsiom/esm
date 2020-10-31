package com.epam.esm.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GiftCertificate extends AbstractEntity {
    private String description;
    private Double price;
    private OffsetDateTime createDate;
    private OffsetDateTime lastUpdateDate;
    private Integer duration;
    private List<String> tags;

    public GiftCertificate() {
    }

    public GiftCertificate(Integer id, String name, String description, Double price, Integer duration, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public GiftCertificate(Integer id, String name, String description, Double price,
                           OffsetDateTime createDate, OffsetDateTime lastUpdateDate, Integer duration, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            this.description = "";
        }
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price == null) {
            this.price = 0.0;
        }
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
        if (duration == null) {
            this.duration = 0;
        }
        this.duration = duration;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        if (tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags = tags;
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
        return name.equals(certificate.name);
    }

    @Override
    public String toString() {
        return "GiftCertificate: {id: " + id + ", name: " + name + ", description: " + description + ", price: " + price +
                ", created: " + createDate + ", updated: " + lastUpdateDate + ", duration: " + duration + "}";
    }
}
