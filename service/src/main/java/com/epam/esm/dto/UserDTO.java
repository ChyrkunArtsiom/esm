package com.epam.esm.dto;

import com.epam.esm.dto.validationmarkers.OrderPostValidation;
import com.epam.esm.dto.validationmarkers.PostValidation;
import com.epam.esm.validator.ValidationMessageManager;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Objects;

/**
 * Data transfer object of {@link com.epam.esm.entity.User}.
 */
public class UserDTO {

    @NotNull(groups = OrderPostValidation.class, message = ValidationMessageManager.ID_INVALID)
    @Digits(integer = 10, fraction = 2, message = ValidationMessageManager.ID_INVALID, groups = OrderPostValidation.class)
    @Positive(message = ValidationMessageManager.ID_INVALID, groups = OrderPostValidation.class)
    private Integer id;

    @NotNull(message = ValidationMessageManager.USER_BLANK_USERNAME,
            groups = PostValidation.class)
    @Pattern(regexp = "^[\\w\\W]{3,45}$", message = ValidationMessageManager.USER_INVALID_USERNAME,
            groups = PostValidation.class)
    private String name;

    @NotNull(message = ValidationMessageManager.USER_BLANK_PASSWORD,
            groups = PostValidation.class)
    @Pattern(regexp = "^[\\w\\W]{3,45}$", message = ValidationMessageManager.USER_INVALID_PASSWORD,
            groups = PostValidation.class)
    private String password;

    @NotNull(message = ValidationMessageManager.USER_BLANK_FIRSTNAME,
            groups = PostValidation.class)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{3,45}$", message = ValidationMessageManager.USER_INVALID_FIRSTNAME,
            groups = PostValidation.class)
    private String firstName;

    @NotNull(message = ValidationMessageManager.USER_BLANK_SECONDNAME,
            groups = PostValidation.class)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]{3,45}$", message = ValidationMessageManager.USER_INVALID_SECONDNAME,
            groups = PostValidation.class)
    private String secondName;

    @NotNull(message = ValidationMessageManager.USER_BLANK_BIRTHDAY,
            groups = PostValidation.class)
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = ValidationMessageManager.USER_INVALID_BIRTHDAY,
            groups = PostValidation.class)
    private String birthday;

    private RoleDTO role;

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
    public UserDTO(Integer id, String name, String password,
                   String firstName, String secondName, String birthday, RoleDTO role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        if (role != null) {
            this.role = new RoleDTO(role);
        }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
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
        return String.format("User id: %d, username: %s, first name: %s, second name: %s, birthday: %s, role: {%s}",
                getId(), getName(), getFirstName(), getSecondName(), getBirthday(), getRole());
    }
}
