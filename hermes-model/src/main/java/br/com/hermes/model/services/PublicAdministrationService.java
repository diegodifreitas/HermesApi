/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BasePublicAdministrationService;
import br.com.hermes.model.daos.PublicAdministrationDAO;
import br.com.hermes.model.entitys.PublicAdministration;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class PublicAdministrationService implements BasePublicAdministrationService {

    @Override
    public PublicAdministration create(PublicAdministration entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            PublicAdministrationDAO publicAdminDao = new PublicAdministrationDAO();
            PublicAdministration divulgation = publicAdminDao.create(connection, entity);
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
    public PublicAdministration readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PublicAdministration publicAdmin = null;
        try {
            PublicAdministrationDAO publicAdminDao = new PublicAdministrationDAO();
            publicAdmin = publicAdminDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return publicAdmin;
    }

    @Override
    public List<PublicAdministration> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<PublicAdministration> publicAdminList = null;
        try {
            PublicAdministrationDAO publicAdminDao = new PublicAdministrationDAO();
            publicAdminList = publicAdminDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return publicAdminList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            PublicAdministrationDAO publicAdminDao = new PublicAdministrationDAO();
            numberOfRegistry = publicAdminDao.countByCriteria(connection, criteria);
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
    public void update(PublicAdministration entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            PublicAdministrationDAO publicAdminDao = new PublicAdministrationDAO();
            publicAdminDao.update(connection, entity);
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
            PublicAdministrationDAO publicAdminDao = new PublicAdministrationDAO();
            publicAdminDao.delete(connection, id);
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

        String cnpj = (String) fields.get("cnpj");
        String name = (String) fields.get("name");
        String phone = (String) fields.get("phone");
        String street = (String) fields.get("street");
        String neighborhood = (String) fields.get("neighborhood");
        String city = (String) fields.get("city");

        if (cnpj == null || cnpj.trim().isEmpty()) {
            errors.put("cnpj", "Campo cnpj é obrigatório!");
        }
        
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Este Campo é obrigatório!");
        }
        
        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Este Campo é obrigatório!");
        }
        
        if (street == null || street.trim().isEmpty()) {
            errors.put("street", "Este Campo é obrigatório!");
        }
        
        if (neighborhood == null || neighborhood.trim().isEmpty()) {
            errors.put("neighborhood", "Este Campo é obrigatório!");
        }
        
        if (city == null || city.trim().isEmpty()) {
            errors.put("city", "Este Campo é obrigatório!");
        }
        
        return errors;
    }
    
}
