package com.epam.esm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class for Tag entity.
 */
@Entity(name = "tags")
@Table(name = "tags", schema = "esm_module2")
public class Tag {

    @Id
    @SequenceGenerator(name = "tags_id_seq", schema = "esm_module2", sequenceName = "tags_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_seq")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Empty constructor.
     */
    public Tag() {

    }

    /**
     * Constructor with name
     *
     * @param name the string name
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Constructor with all fields
     *
     * @param id   the id
     * @param name the string name
     */
    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        Tag tag = (Tag) obj;
        return getName().equals(tag.getName());
    }

    @Override
    public String toString() {
        return String.format("Tag id: %d, name: %s", getId(), getName());
    }
}
