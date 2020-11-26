package com.epam.esm.entity;

import javax.persistence.*;
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

    /**
     * Empty constructor.
     */
    public User() {
    }

    /**
     * Constructor with name and password
     *
     * @param name      the name
     * @param password  the password
     */
    public User(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Constructor with all fields
     *
     * @param id        the id
     * @param name      the name
     * @param password  the password
     */
    public User(Integer id, String name, char[] password) {
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
        User user = (User) obj;
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
