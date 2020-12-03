package com.epam.esm.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class for User entity.
 */
@Entity(name = "users")
@Table(name = "users", schema = "esm_module2")
public class User {

    @Id
    @SequenceGenerator(name = "users_id_seq", schema = "esm_module2", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private char[] password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "second_name")
    private String secondName;

    @Column(nullable = false, name = "birthday")
    private LocalDate birthday;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "role_id", referencedColumnName = "id")
    private Role role;

    /**
     * Empty constructor.
     */
    public User() {
    }

    /**
     * Constructor without an id
     *
     * @param name       the name
     * @param password   the password
     * @param firstName  the first name
     * @param secondName the second name
     * @param birthday   the birthday
     * @param role       the role
     */
    public User(String name, char[] password, String firstName,
                String secondName, LocalDate birthday, Role role) {
        this.name = name;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.role = new Role(role);
    }

    /**
     * Constructor with all fields
     *
     * @param id         the id
     * @param name       the name
     * @param password   the password
     * @param firstName  the first name
     * @param secondName the second name
     * @param birthday   the birthday
     * @param role       the {@link Role} object
     */
    public User(Integer id, String name,
                char[] password, String firstName,
                String secondName, LocalDate birthday, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.role = new Role(role);
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
        User user = (User) obj;
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
