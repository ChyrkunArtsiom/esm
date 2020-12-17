package com.epam.esm.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "roles")
@Table(name = "roles", schema = "esm_module2")
public class Role {
    @Id
    @SequenceGenerator(
            name = "roles_id_seq", schema = "esm_module2", sequenceName = "roles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    
    public Role(Role role) {
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Role role = (Role) obj;
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
