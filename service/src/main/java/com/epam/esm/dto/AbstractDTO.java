package com.epam.esm.dto;

/**
 * Abstract class to classify all data transfer objects.
 */
public abstract class AbstractDTO {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            id = 0;
        }
        this.id = id;
    }
}
