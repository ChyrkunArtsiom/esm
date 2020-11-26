package com.epam.esm.dto;

import com.epam.esm.dto.validationmarkers.*;
import com.epam.esm.validator.ValidationMessageManager;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Data transfer object of {@link com.epam.esm.entity.GiftCertificate}.
 */
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> {

    @NotNull(groups = OrderPostValidation.class, message = ValidationMessageManager.ID_INVALID)
    @Digits(integer = 10, fraction = 2, message = ValidationMessageManager.ID_INVALID, groups = OrderPostValidation.class)
    @Positive(message = ValidationMessageManager.ID_INVALID, groups = {OrderPostValidation.class, OrderPutValidation.class})
    private Integer id;

    @NotNull(message = ValidationMessageManager.BLANK_CERTIFICATE_NAME,
            groups = {PostValidation.class, PutValidation.class, DeleteValidation.class})
    @Pattern(regexp = "^[\\w\\W]{3,45}$", message = ValidationMessageManager.CERTIFICATE_NAME_WRONG_SIZE,
            groups = {PostValidation.class, PutValidation.class, DeleteValidation.class})
    private String name;

    /** A string of description. */
    @NotNull(message = ValidationMessageManager.BLANK_CERTIFICATE_DESCRIPTION,
            groups = PostValidation.class)
    @Pattern(regexp = "^[\\w\\W]{3,45}$", message = ValidationMessageManager.CERTIFICATE_DESCRIPTION_WRONG_SIZE,
            groups = {PostValidation.class, PutValidation.class})
    private String description;

    /** A BigDecimal of price. */
    @NotNull(message = ValidationMessageManager.CERTIFICATE_PRICE_INVALID, groups = PostValidation.class)
    @Digits(integer = 10, fraction = 2, message = ValidationMessageManager.CERTIFICATE_PRICE_INVALID,
            groups = {PostValidation.class, PutValidation.class})
    @Positive(message = ValidationMessageManager.CERTIFICATE_PRICE_INVALID,
            groups = {PostValidation.class, PutValidation.class})
    private BigDecimal price;

    /** A string of date of creation. */
    private String createDate;

    /** A string of date of last update. */
    private String lastUpdateDate;

    /** A duration in days. */
    @NotNull(message = ValidationMessageManager.CERTIFICATE_DURATION_INVALID,
            groups = PostValidation.class)
    @Digits(integer = 10, fraction = 0, message = ValidationMessageManager.CERTIFICATE_DURATION_INVALID,
            groups = {PostValidation.class, PutValidation.class})
    @Positive(message = ValidationMessageManager.CERTIFICATE_DURATION_INVALID,
            groups = {PostValidation.class, PutValidation.class})
    private Integer duration;

    /** A set of tag names. */
    @Valid
    private Set<TagDTO> tags;

    /**
     * Empty constructor.
     */
    public GiftCertificateDTO() {
    }

    /**
     * Constructor without dates.
     *
     * @param id          the id
     * @param name        the string of name
     * @param description the string of description
     * @param price       the BigDecimal of price
     * @param duration    the duration
     * @param tags        the list of {@link TagDTO} objects
     */
    public GiftCertificateDTO(Integer id, String name, String description, BigDecimal price,
                              Integer duration, Set<TagDTO> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    /**
     * Constructor with all fields.
     *
     * @param id             the id
     * @param name           the string of name
     * @param description    the string of description
     * @param price          the BigDecimal of price
     * @param createDate     the string of date of creation
     * @param lastUpdateDate the string of date of last update
     * @param duration       the duration
     * @param tags           the list of {@link TagDTO} objects
     */
    public GiftCertificateDTO(Integer id, String name, String description, BigDecimal price, String createDate,
                              String lastUpdateDate, Integer duration, Set<TagDTO> tags) {
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

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name);
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
        return name.equals(certificate.getName()) && description.equals(certificate.getDescription())
                && price.equals(certificate.getPrice()) && duration.equals(certificate.getDuration());
    }

    @Override
    public String toString() {
        return String.format("GiftCertificate: {id: %d, name: %s, description: %s, " +
                        "price: %f, created: %s, updated: %s, duration: %d}",
                getId(), getName(), getDescription(), getPrice(), getCreateDate(), getLastUpdateDate(), getDuration());
    }
}
