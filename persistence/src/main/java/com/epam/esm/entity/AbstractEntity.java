package com.epam.esm.entity;

/**
 * Abstract class for entities to classify all entity classes.
 */
public abstract class AbstractEntity {
    /**
     * An id.
     */
    protected Integer id;
    /**
     * A string of name.
     */
    protected String name;

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
}
