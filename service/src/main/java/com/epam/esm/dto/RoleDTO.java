package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.Role}.
 */
public class RoleDTO extends RepresentationModel<RoleDTO> implements GrantedAuthority {
    private Integer id;

    private String name;

    /**
     * Empty constructor.
     */
    public RoleDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id   the id
     * @param name the name
     */
    public RoleDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Coping constructor.
     *
     * @param role the role
     */
    public RoleDTO(RoleDTO role) {
        this.id = role.getId();
        this.name = role.getName();
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
    public String getAuthority() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RoleDTO role = (RoleDTO) obj;
        return getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return String.format("Role id: %d, name: %s", getId(), getName());
    }
}
