package com.epam.esm.entity;

import java.util.Objects;

public class Tag extends AbstractEntity {

    public Tag() {

    }

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        Tag tag = (Tag) obj;
        return id.equals(tag.id) && name.equals(tag.name);
    }

    @Override
    public String toString() {
        return "Tag id: " + this.id + ", name: " + this.name;
    }
}
