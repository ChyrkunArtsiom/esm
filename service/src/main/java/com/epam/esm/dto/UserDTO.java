package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.User}.
 */
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDTO extends RepresentationModel<UserDTO> {

    private Integer id;

    private String name;

    private char[] password;

    /**
     * Empty constructor.
     */
    public UserDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id   the id
     * @param name the string of name
     */
    public UserDTO(Integer id, String name, char[] password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDTO user = (UserDTO) obj;
        return getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return String.format("User id: %d, name: %s", getId(), getName());
    }
}
