package com.epam.esm.dto;

import com.epam.esm.validator.ValidationMessageManager;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class GiftCertificateDTO extends AbstractDTO {
    private Integer id;

    @NotBlank(message = ValidationMessageManager.BLANK_CERTIFICATE_NAME)
    @Size(min = 3, max = 45, message = ValidationMessageManager.CERTIFICATE_NAME_WRONG_SIZE)
    private String name;

    @NotBlank(message = ValidationMessageManager.BLANK_CERTIFICATE_DESCRIPTION)
    @Size(min = 3, max = 45, message = ValidationMessageManager.CERTIFICATE_DESCRIPTION_WRONG_SIZE)
    private String description;

    @Digits(integer = 10, fraction = 2, message = ValidationMessageManager.CERTIFICATE_PRICE_INVALID)
    @Positive(message = ValidationMessageManager.CERTIFICATE_PRICE_INVALID)
    private BigDecimal price;

    private String createDate;

    private String lastUpdateDate;

    @Digits(integer = 10, fraction = 0, message = ValidationMessageManager.CERTIFICATE_DURATION_INVALID)
    @Positive(message = ValidationMessageManager.CERTIFICATE_DURATION_INVALID)
    private Integer duration;

    private List<String> tags;

    public GiftCertificateDTO() {
    }

    public GiftCertificateDTO(Integer id, String name, String description, BigDecimal price,
                              Integer duration, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public GiftCertificateDTO(Integer id, String name, String description, BigDecimal price, String createDate,
                              String lastUpdateDate, Integer duration, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
        this.tags = tags;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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
        GiftCertificateDTO certificate = (GiftCertificateDTO) obj;
        return name.equals(certificate.name);
    }

    @Override
    public String toString() {
        return "GiftCertificate: {id: " + id + ", name: " + name + ", description: " + description + ", price: " + price +
                ", created: " + createDate + ", updated: " + lastUpdateDate + ", duration: " + duration + "}";
    }
}
