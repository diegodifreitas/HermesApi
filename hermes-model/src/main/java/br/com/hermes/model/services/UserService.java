/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BaseUserService;
import br.com.hermes.model.daos.UserDAO;
import br.com.hermes.model.entitys.User;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author NPDI-04
 */
public class UserService implements BaseUserService {

    @Override
    public User create(User entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            UserDAO userDao = new UserDAO();
            User divulgation = userDao.create(connection, entity);
            connection.commit();
            connection.close();
            return divulgation;
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
    }

    @Override
    public User readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        User user = null;
        try {
            UserDAO userDao = new UserDAO();
            user = userDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return user;
    }

    @Override
    public List<User> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<User> userList = null;
        try {
            UserDAO userDao = new UserDAO();
            userList = userDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return userList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            UserDAO userDao = new UserDAO();
            numberOfRegistry = userDao.countByCriteria(connection, criteria);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return numberOfRegistry;
    }

    @Override
    public void update(User entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            UserDAO userDao = new UserDAO();
            userDao.update(connection, entity);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            UserDAO userDao = new UserDAO();
            userDao.delete(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        Map<String, String> errors = new HashMap<>();

        String name = (String) fields.get("name");
        String password = (String) fields.get("password");

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Este Campo é obrigatório!");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Campo descrição é obrigatório!");
        }
        return errors;
    }
}