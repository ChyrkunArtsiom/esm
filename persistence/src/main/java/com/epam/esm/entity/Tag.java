package com.epam.esm.entity;

import java.util.Objects;

/**
 * Class for Tag entity.
 */
public class Tag {

    private Integer id;

    private String name;

    /**
     * Empty constructor.
     */
    public Tag() {

    }

    /**
     * Constructor with all fields.
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
        return getId().equals(tag.getId()) && getName().equals(tag.getName());
    }

    @Override
    public String toString() {
        return String.format("Tag id: %d, name: %s", getId(), getName());
    }
}
