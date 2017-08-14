/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BaseResourceService;
import br.com.hermes.model.daos.ResourceDAO;
import br.com.hermes.model.entitys.Resource;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class ResourceService implements BaseResourceService {

    @Override
    public Resource create(Resource entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            ResourceDAO resourceDao = new ResourceDAO();
            Resource divulgation = resourceDao.create(connection, entity);
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
    public Resource readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Resource resource = null;
        try {
            ResourceDAO resourceDao = new ResourceDAO();
            resource = resourceDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return resource;
    }

    @Override
    public List<Resource> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<Resource> resourceList = null;
        try {
            ResourceDAO resourceDao = new ResourceDAO();
            resourceList = resourceDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return resourceList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            ResourceDAO resourceDao = new ResourceDAO();
            numberOfRegistry = resourceDao.countByCriteria(connection, criteria);
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
    public void update(Resource entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            ResourceDAO resourceDao = new ResourceDAO();
            resourceDao.update(connection, entity);
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
            ResourceDAO resourceDao = new ResourceDAO();
            resourceDao.delete(connection, id);
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

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Campo nome é obrigatório!");
        }
        
        return errors;
    }

}
