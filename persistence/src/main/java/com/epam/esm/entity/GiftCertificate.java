package com.epam.esm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Class for Certificate entity.
 */
@Entity(name = "certificates")
@Table(name = "certificates", schema = "esm_module2")
public class GiftCertificate implements Serializable {

    @Id
    @SequenceGenerator(
            name = "certificates_id_seq", sequenceName = "esm_module2.certificates_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificates_id_seq")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    /** A string of description. */
    @Column(nullable = false)
    private String description;

    /** A Double of price. */
    @Column(nullable = false)
    private Double price;

    /** An OffSetDateTime of date of creation. */
    @Column(name = "create_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createDate;

    /** An OffSetDateTime of date of last update. */
    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastUpdateDate;

    /** A duration in days. */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /** A set of tag names. */
    @ManyToMany(/*fetch = FetchType.EAGER, */cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "certificate_tag", schema = "esm_module2",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    /**
     * Empty constructor.
     */
    public GiftCertificate() {
    }

    /**
     * Constructor without dates.
     *
     * @param name        the string of name
     * @param description the string of description
     * @param price       the Double of price
     * @param duration    the duration
     * @param tags        the list of tag names
     */
    public GiftCertificate(String name, String description, Double price, Integer duration, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        if (tags != null) {
            this.tags = new HashSet<>(tags);
        }
    }

    /**
     * Constructor with all fields.
     *
     * @param name           the string of name
     * @param description    the string of description
     * @param price          the Double of price
     * @param createDate     the OffSetDateTime of date of creation
     * @param lastUpdateDate the OffSetDateTime of date of last update
     * @param duration       the duration
     * @param tags           the list of tag names
     */
    public GiftCertificate(String name, String description, Double price,
                           OffsetDateTime createDate, OffsetDateTime lastUpdateDate, Integer duration, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
        if (tags != null) {
            this.tags = new HashSet<>(tags);
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        if (tags != null) {
            this.tags = new HashSet<>(tags);
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
