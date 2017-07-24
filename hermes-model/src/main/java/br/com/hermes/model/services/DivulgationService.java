/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BaseDivulgationService;
import br.com.hermes.model.daos.DivulgationDAO;
import br.com.hermes.model.entitys.Divulgation;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Diego Dulval
 */
public class DivulgationService implements BaseDivulgationService {

    @Override
    public Divulgation create(Divulgation entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            DivulgationDAO divulgationDao = new DivulgationDAO();
            Divulgation divulgation = divulgationDao.create(connection, entity);
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
    public Divulgation readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Divulgation divulgation = null;
        try {
            DivulgationDAO divulgationDao = new DivulgationDAO();
            divulgation = divulgationDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return divulgation;
    }

    @Override
    public List<Divulgation> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<Divulgation> divulgationList = null;
        try {
            DivulgationDAO divulgacaoDao = new DivulgationDAO();
            divulgationList = divulgacaoDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return divulgationList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            DivulgationDAO divulgationDao = new DivulgationDAO();
            numberOfRegistry = divulgationDao.countByCriteria(connection, criteria);
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
    public void update(Divulgation entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            DivulgationDAO divulgationDao = new DivulgationDAO();
            divulgationDao.update(connection, entity);
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
            DivulgationDAO divulgationDao = new DivulgationDAO();
            divulgationDao.delete(connection, id);
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

        String title = (String) fields.get("title");
        String description = (String) fields.get("description");
        String type = (String) fields.get("type");

        if (title == null || title.trim().isEmpty()) {
            errors.put("title", "Este Campo é obrigatório!");
        }

        if (description == null || description.trim().isEmpty()) {
            errors.put("description", "Campo descrição é obrigatório!");
        }
        
        if (type == null || type.trim().isEmpty()) {
            errors.put("type", "Campo Tipo não foi definido!");
        }

        return errors;
    }
}
