/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.PermissionCriteria;
import br.com.hermes.model.entitys.Permission;
import br.com.hermes.model.enums.PrivilegeEnum;
import br.com.hermes.model.services.ResourceService;
import br.com.hermes.model.services.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Braian
 */
public class PermissionDAO implements BaseDAO<Permission> {
    
    @Override
    public Permission create(Connection connection, Permission entity) throws Exception {
        String sql
                = "INSERT INTO permission "
                + "(user_fk, resource_fk, date, privilege) "
                + "VALUES (?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setLong(++indexQueryParameter, entity.getUser().getId());
            preparedStatement.setLong(++indexQueryParameter, entity.getResource().getId());
            preparedStatement.setTimestamp(++indexQueryParameter, (Timestamp) entity.getDate());
            preparedStatement.setString(++indexQueryParameter, entity.getPrivilege().toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
            }

            resultSet.close();
            preparedStatement.close();
        }

        return entity;
    }

    @Override
    public Permission readById(Connection connection, Long id) throws Exception {
        String sql
                = "SELECT * FROM permission "
                + "WHERE id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();
        UserService userService = new UserService();
        ResourceService resourceService = new ResourceService();

        Permission permission = null;
        while (resultSet.next()) {
            permission = new Permission(resultSet.getLong("id"),
                                        userService.readById(resultSet.getLong("user_fk")),
                                        resourceService.readById(resultSet.getLong("resource_fk")),
                                        resultSet.getTimestamp("date"),
                                        PrivilegeEnum.valueOf(resultSet.getString("privilege")));
        }

        resultSet.close();
        ps.close();

        return permission;
    }

    @Override
    public List<Permission> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<Permission> permissionList = new ArrayList<>();
        String sql
                = "SELECT * FROM permission "
                + "WHERE 1=1";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY date";

        if (limit != null && limit > 0) {
            sql += " LIMIT " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " OFFSET " + offset;
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            permissionList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return permissionList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM permission a WHERE 1=1 ";
        if (criteria != null) {
            sql += applyCriteria(criteria);
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Long numberOfRegistry = 0L;
        if (resultSet.next()) {
            numberOfRegistry = resultSet.getLong("count");
        }
        resultSet.close();
        statement.close();

        return numberOfRegistry;
    }

    @Override
    public void update(Connection connection, Permission entity) throws Exception {
        String sql = "UPDATE permission SET user_fk=?, resource_fk=?, date=?, privilege=? WHERE id=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        int i = 0;
        ps.setLong(++i, entity.getUser().getId());
        ps.setLong(++i, entity.getResource().getId());
        ps.setTimestamp(++i, entity.getDate());
        ps.setString(++i, entity.getPrivilege().name());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM permission WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        Long infoUserEQ = (Long) criteria.get(PermissionCriteria.USER_EQ);
        if (infoUserEQ != null && infoUserEQ > 0) {
            sql += " AND user_fk ='" + infoUserEQ + "'";
        }
        
        Long infoResourceEQ = (Long) criteria.get(PermissionCriteria.RESOURCE_EQ);
        if (infoResourceEQ != null && infoResourceEQ > 0) {
            sql += " AND resource_fk = '" + infoResourceEQ + "'";
        }
        
        String infoPrivilegeEQ = (String) criteria.get(PermissionCriteria.PRIVILEGE_EQ);
        if (infoPrivilegeEQ != null) {
            sql += " AND privilege = '" + infoPrivilegeEQ + "'";
        }

        return sql;
    }

    @Override
    public Permission parser(ResultSet resultSet) throws SQLException {
        
        UserService userService = new UserService();
        ResourceService resourceService = new ResourceService();
        
        Permission permission = null;
        try {
            permission = new Permission(
                    resultSet.getLong("id"),
                    userService.readById(resultSet.getLong("user_fk")),
                    resourceService.readById(resultSet.getLong("resource_fk")),
                    resultSet.getTimestamp("date"),
                    PrivilegeEnum.valueOf(resultSet.getString("privilege"))
            );
        } catch (Exception ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permission;
    }
}
