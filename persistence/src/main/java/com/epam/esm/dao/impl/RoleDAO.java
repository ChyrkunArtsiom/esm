package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Role;
import com.epam.esm.exception.NoRoleException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAO implements AbstractDAO<Role> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role create(Role entity) {
        throw new UnsupportedOperationException("Role is not supported by create method.");
    }

    @Override
    public Role read(int id) {
        throw new UnsupportedOperationException("Role is not supported by read by id method.");
    }

    @Override
    public Role read(String name) {
        try {
            TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM roles r WHERE r.name=:name", Role.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoRoleException(String.format("Role with name = {%s} doesn't exist.", name), ex,
                    name);
        }
    }

    @Override
    public Role update(Role entity) {
        throw new UnsupportedOperationException("Role is not supported by update method.");
    }

    @Override
    public boolean delete(Role entity) {
        throw new UnsupportedOperationException("Role is not supported by delete method.");
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Role is not supported by delete by id method.");
    }

    @Override
    public List<Role> readAll() {
        throw new UnsupportedOperationException("Role is not supported by read all method.");
    }
}
