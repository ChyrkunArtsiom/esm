package com.epam.esm.dto;

import com.epam.esm.validator.ValidationMessageManager;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.Tag}.
 */
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDTO extends RepresentationModel<TagDTO> {

    private Integer id;

    @NotBlank(message = ValidationMessageManager.BLANK_TAG_NAME)
    @Size(min = 3, max = 45, message = ValidationMessageManager.TAG_NAME_WRONG_SIZE)
    private String name;

    /**
     * Empty constructor.
     */
    public TagDTO() {

    }

    /**
     * Constructor with all fields.
     *
     * @param id   the id
     * @param name the string of name
     */
    public TagDTO(Integer id, String name) {
        setId(id);
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
        return Objects.hash(getId(), name);
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
        return getId().equals(tag.getId()) && name.equals(tag.name);
    }

    @Override
    public String toString() {
        return String.format("Tag id: %d, name: %s", getId(), getName());
    }
}
