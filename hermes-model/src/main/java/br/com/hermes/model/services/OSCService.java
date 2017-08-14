/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BaseOSCService;
import br.com.hermes.model.daos.OSCDAO;
import br.com.hermes.model.entitys.OSC;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class OSCService implements BaseOSCService{
    
    @Override
    public OSC create(OSC entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            OSCDAO oscDao = new OSCDAO();
            OSC divulgation = oscDao.create(connection, entity);
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
    public OSC readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        OSC osc = null;
        try {
            OSCDAO oscDao = new OSCDAO();
            osc = oscDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return osc;
    }

    @Override
    public List<OSC> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<OSC> oscList = null;
        try {
            OSCDAO oscDao = new OSCDAO();
            oscList = oscDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return oscList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            OSCDAO oscDao = new OSCDAO();
            numberOfRegistry = oscDao.countByCriteria(connection, criteria);
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
    public void update(OSC entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            OSCDAO oscDao = new OSCDAO();
            oscDao.update(connection, entity);
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
            OSCDAO oscDao = new OSCDAO();
            oscDao.delete(connection, id);
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
        String cnpj = (String) fields.get("cnpj");
        String phone = (String) fields.get("phone");
        String street = (String) fields.get("street");
        String neighborhood = (String) fields.get("neighborhood");
        String city = (String) fields.get("city");
        Boolean valid = (Boolean) fields.get("valid");
        String status = (String) fields.get("status");

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Campo nome é obrigatório!");
        }
        
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Este Campo é obrigatório!");
        }
        
        if (cnpj == null || cnpj.trim().isEmpty()) {
            errors.put("cnpj", "Campo nome é obrigatório!");
        }
        
        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Este Campo é obrigatório!");
        }
        
        if (street == null || street.trim().isEmpty()) {
            errors.put("street", "Campo nome é obrigatório!");
        }
        
        if (neighborhood == null || neighborhood.trim().isEmpty()) {
            errors.put("neighborhood", "Campo nome é obrigatório!");
        }
        
        if (city == null || city.trim().isEmpty()) {
            errors.put("city", "Este Campo é obrigatório!");
        }
        
        if (valid == null) {
            errors.put("valid", "Este Campo é obrigatório!");
        }
        
        if (status == null || status.trim().isEmpty()) {
            errors.put("status", "Este Campo é obrigatório!");
        }
        
        return errors;
    }
}
