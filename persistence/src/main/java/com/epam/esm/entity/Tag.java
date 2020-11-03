package com.epam.esm.entity;

import java.util.Objects;

/**
 * Class for Tag entity.
 */
public class Tag extends AbstractEntity {

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
        setId(id);
        setName(name);
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
