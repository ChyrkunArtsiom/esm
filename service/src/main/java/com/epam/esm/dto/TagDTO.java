package com.epam.esm.dto;

import com.epam.esm.dto.validationmarkers.DeleteValidation;
import com.epam.esm.dto.validationmarkers.PostValidation;
import com.epam.esm.dto.validationmarkers.PutValidation;
import com.epam.esm.validator.ValidationMessageManager;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.Tag}.
 */
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDTO extends RepresentationModel<TagDTO> {

    private Integer id;

    @NotBlank(message = ValidationMessageManager.BLANK_TAG_NAME,
            groups = {PostValidation.class, PutValidation.class, DeleteValidation.class})
    @Pattern(regexp = "^[a-zA-ZА-Яа-я]{3,45}$", message = ValidationMessageManager.TAG_INVALID_NAME,
            groups = {PostValidation.class, PutValidation.class, DeleteValidation.class})
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
