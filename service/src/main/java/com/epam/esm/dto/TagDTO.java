package com.epam.esm.dto;

import com.epam.esm.exception.ValidationMessageManager;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagDTO extends AbstractDTO{

    private Integer id;
    @NotBlank(message = ValidationMessageManager.BLANK_TAG)
    @Size(min = 3, max = 45, message = ValidationMessageManager.TAG_WRONG_SIZE)
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
