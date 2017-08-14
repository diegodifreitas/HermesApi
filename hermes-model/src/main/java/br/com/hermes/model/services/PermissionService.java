/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.services;

import br.com.hermes.model.base.services.BasePermissionService;
import br.com.hermes.model.daos.PermissionDAO;
import br.com.hermes.model.entitys.Permission;
import br.com.hermes.model.infra.ConnectionManager;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class PermissionService implements BasePermissionService {
    
    @Override
    public Permission create(Permission entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            PermissionDAO permissionDao = new PermissionDAO();
            Permission divulgation = permissionDao.create(connection, entity);
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
    public Permission readById(Long id) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Permission permission = null;
        try {
            PermissionDAO permissionDao = new PermissionDAO();
            permission = permissionDao.readById(connection, id);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return permission;
    }

    @Override
    public List<Permission> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        List<Permission> permissionList = null;
        try {
            PermissionDAO permissionDao = new PermissionDAO();
            permissionList = permissionDao.readByCriteria(connection, criteria, limit, offset);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }
        return permissionList;
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        Long numberOfRegistry = 0L;
        try {
            PermissionDAO permissionDao = new PermissionDAO();
            numberOfRegistry = permissionDao.countByCriteria(connection, criteria);
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
    public void update(Permission entity) throws Exception {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            PermissionDAO permissionDao = new PermissionDAO();
            permissionDao.update(connection, entity);
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
            PermissionDAO permissionDao = new PermissionDAO();
            permissionDao.delete(connection, id);
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

        Long user = (Long) fields.get("user");
        Long resource = (Long) fields.get("resource");
        Timestamp date = (Timestamp) fields.get("date");
        Long privilege = (Long) fields.get("privilege");

        if (user == null || user <= 0) {
            errors.put("user", "Este Campo é obrigatório!");
        }
        
        if (resource == null || resource <= 0) {
            errors.put("resource", "Este Campo é obrigatório!");
        }
        
        if (privilege == null || privilege <= 0) {
            errors.put("privilege", "Este Campo é obrigatório!");
        }
        
        return errors;
    }
}
