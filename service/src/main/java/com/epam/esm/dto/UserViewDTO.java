package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Relation(itemRelation = "user", collectionRelation = "users")
public class UserViewDTO extends RepresentationModel<UserViewDTO> {

    private Integer id;

    private String name;

    private String firstName;

    private String secondName;

    private String birthday;

    public UserViewDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id   the id
     * @param name the string of name
     */
    public UserViewDTO(Integer id, String name,
                       String firstName, String secondName, String birthday) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserViewDTO user = (UserViewDTO) obj;
        return getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return String.format("User id: %d, username: %s, first name: %s, second name: %s, birthday: %s",
                getId(), getName(), getFirstName(), getSecondName(), getBirthday());
    }
}
