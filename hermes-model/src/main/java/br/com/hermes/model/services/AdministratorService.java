/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BaseAdministratorService;
import br.com.hermes.model.daos.AdministratorDAO;
import br.com.hermes.model.entitys.Administrator;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class AdministratorService implements BaseAdministratorService {

    @Override
    public Administrator create(Administrator entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            AdministratorDAO adminDao = new AdministratorDAO();
            Administrator divulgation = adminDao.create(connection, entity);
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
    public Administrator readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Administrator admin = null;
        try {
            AdministratorDAO adminDao = new AdministratorDAO();
            admin = adminDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return admin;
    }

    @Override
    public List<Administrator> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<Administrator> adminList = null;
        try {
            AdministratorDAO adminDao = new AdministratorDAO();
            adminList = adminDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return adminList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            AdministratorDAO adminDao = new AdministratorDAO();
            numberOfRegistry = adminDao.countByCriteria(connection, criteria);
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
    public void update(Administrator entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            AdministratorDAO adminDao = new AdministratorDAO();
            adminDao.update(connection, entity);
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
            AdministratorDAO adminDao = new AdministratorDAO();
            adminDao.delete(connection, id);
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
        String email = (String) fields.get("email");

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Campo nome é obrigatório!");
        }
        
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Este Campo é obrigatório!");
        }
        
        return errors;
    }

}
