/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BasePublicServerService;
import br.com.hermes.model.daos.PublicServerDAO;
import br.com.hermes.model.entitys.PublicServer;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class PublicServerService implements BasePublicServerService {
   
    @Override
    public PublicServer create(PublicServer entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            PublicServerDAO publicServerDao = new PublicServerDAO();
            PublicServer divulgation = publicServerDao.create(connection, entity);
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
    public PublicServer readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PublicServer publicServer = null;
        try {
            PublicServerDAO publicServerDao = new PublicServerDAO();
            publicServer = publicServerDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return publicServer;
    }

    @Override
    public List<PublicServer> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<PublicServer> publicServerList = null;
        try {
            PublicServerDAO publicServerDao = new PublicServerDAO();
            publicServerList = publicServerDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return publicServerList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            PublicServerDAO publicServerDao = new PublicServerDAO();
            numberOfRegistry = publicServerDao.countByCriteria(connection, criteria);
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
    public void update(PublicServer entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            PublicServerDAO publicServerDao = new PublicServerDAO();
            publicServerDao.update(connection, entity);
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
            PublicServerDAO publicServerDao = new PublicServerDAO();
            publicServerDao.delete(connection, id);
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
        String office = (String) fields.get("office");

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Campo nome é obrigatório!");
        }
        
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Este Campo é obrigatório!");
        }
        
        if (office == null || office.trim().isEmpty()) {
            errors.put("office", "Este Campo é obrigatório!");
        }
        
        return errors;
    }
}
