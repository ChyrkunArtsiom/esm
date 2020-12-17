package com.epam.esm.service.impl;

import com.epam.esm.EncoderConfiguration;
import com.epam.esm.dao.impl.RoleDAO;
import com.epam.esm.dao.impl.UserDAO;
import com.epam.esm.dto.AuthenticationUser;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserViewDTO;
import com.epam.esm.dto.models.AuthenticationRequest;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.BadAuthenticationException;
import com.epam.esm.exception.NoUserException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.jwt.JwtUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for interacting with {@link com.epam.esm.entity.User}. Implements {@link AbstractService}.
 */
@Service
@ComponentScan(basePackageClasses = {UserDAO.class, EncoderConfiguration.class})
public class UserService implements AbstractService<UserViewDTO, UserDTO>, UserDetailsService {

    private UserDAO dao;

    private RoleDAO roleDAO;

    private PasswordEncoder encoder;

    private AuthenticationManager manager;

    private JwtUtilImpl jwtTokenUtil;

    @Autowired
    public void setJwtTokenUtil(JwtUtilImpl jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setManager(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    @Transactional
    public UserViewDTO create(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        Role role = roleDAO.read("ROLE_USER");
        user.setRole(role);
        user.setPassword(encoder.encode(dto.getPassword()).toCharArray());
        user = dao.create(user);
        return UserMapper.toUserViewDTO(user);
    }

    @Override
    public UserViewDTO read(int id) {
        User user = dao.read(id);
        return UserMapper.toUserViewDTO(user);
    }

    public UserViewDTO readOpenUser(int id) {
        User user = dao.read(id);
        return UserMapper.toUserViewDTO(user);
    }

    @Override
    public List<UserViewDTO> readAll() {
        List<UserViewDTO> dtos;
        List<User> entities = dao.readAll();
        dtos = entities.stream().map(UserMapper::toUserViewDTO).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<UserViewDTO> readPaginated(Integer page, Integer size) {
        List<UserViewDTO> dtos;
        List<User> entities = dao.readPaginated(page, size);
        dtos = entities.stream().map(UserMapper::toUserViewDTO).collect(Collectors.toList());
        return dtos;
    }

    public List<UserViewDTO> readOpenUsersPaginated(Integer page, Integer size) {
        List<UserViewDTO> dtos;
        List<User> entities = dao.readPaginated(page, size);
        dtos = entities.stream().map(UserMapper::toUserViewDTO).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public UserViewDTO update(UserDTO entity) {
        throw new UnsupportedOperationException("User is not supported by update method.");
    }

    @Override
    public boolean delete(UserDTO dto) {
        throw new UnsupportedOperationException("User is not supported by delete method.");
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("User is not supported by delete method.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = dao.read(username);
            return new AuthenticationUser(UserMapper.toDto(user));
        } catch (NoUserException ex) {
            throw new BadAuthenticationException(ex);
        }
    }

    @Override
    public int getLastPage(Integer size) {
        return dao.getLastPage(size);
    }

    public String authorize(AuthenticationRequest request) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            final UserDetails userDetails = loadUserByUsername(request.getUsername());
            return jwtTokenUtil.generateToken(userDetails);
        }catch (BadCredentialsException ex) {
            throw new BadAuthenticationException(ex);
        }
    }
}
