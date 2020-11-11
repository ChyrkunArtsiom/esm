package com.epam.esm.entity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Class for Certificate entity.
 */
/*@Entity*/
public class GiftCertificate {

/*    @Id
    @GeneratedValue*/
    private Integer id;

    private String name;
    /** A string of description. */
    private String description;
    /** A Double of price. */
    private Double price;
    /** An OffSetDateTime of date of creation. */

    private OffsetDateTime createDate;
    /** An OffSetDateTime of date of last update. */
    private OffsetDateTime lastUpdateDate;
    /** A duration in days. */
    private Integer duration;
    /** A list of tag names. */
    private List<Tag> tags;

    /**
     * Empty constructor.
     */
    public GiftCertificate() {
    }

    /**
     * Constructor without dates.
     *
     * @param id          the id
     * @param name        the string of name
     * @param description the string of description
     * @param price       the Double of price
     * @param duration    the duration
     * @param tags        the list of tag names
     */
    public GiftCertificate(Integer id, String name, String description, Double price, Integer duration, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        if (tags == null) {
            this.tags = new ArrayList<>();
        } else {
            this.tags = new ArrayList<>(tags);
        }
    }

    /**
     * Constructor with all fields.
     *
     * @param id             the id
     * @param name           the string of name
     * @param description    the string of description
     * @param price          the Double of price
     * @param createDate     the OffSetDateTime of date of creation
     * @param lastUpdateDate the OffSetDateTime of date of last update
     * @param duration       the duration
     * @param tags           the list of tag names
     */
    public GiftCertificate(Integer id, String name, String description, Double price,
                           OffsetDateTime createDate, OffsetDateTime lastUpdateDate, Integer duration, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
        if (tags == null) {
            this.tags = new ArrayList<>();
        } else {
            this.tags = new ArrayList<>(tags);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            id = 0;
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        if (tags == null) {
            this.tags = new ArrayList<>();
        } else {
            this.tags = new ArrayList<>(tags);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
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
        return name.equals(certificate.getName()) && description.equals(certificate.getDescription())
                && price.equals(certificate.getPrice()) && duration.equals(certificate.getDuration());
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String created = null;
        String updated = null;
        if (getCreateDate() != null) {
            created = getCreateDate().format(df);
        }
        if (getLastUpdateDate() != null) {
            updated = getLastUpdateDate().format(df);
        }
        return String.format("GiftCertificate: {id: %d, name: %s, description: %s, " +
                        "price: %f, created: %s, updated: %s, duration: %d}",
                getId(), getName(), getDescription(), getPrice(), created, updated, getDuration());
    }
}
