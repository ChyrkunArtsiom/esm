package com.epam.esm.controller.dto;

import java.util.Objects;

public class TagDTO extends EntityDTO {
    private Integer id;
    private String name;

    public TagDTO() {

    }

    public TagDTO(Integer id, String name) {
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
        TagDTO tag = (TagDTO) obj;
        return id.equals(tag.id) && name.equals(tag.name);
    }

    @Override
    public String toString() {
        return "Tag id: " + this.id + ", name: " + this.name;
    }
}
