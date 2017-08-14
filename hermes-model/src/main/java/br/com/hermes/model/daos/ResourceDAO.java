/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.ResourceCriteria;
import br.com.hermes.model.entitys.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Braian
 */
public class ResourceDAO implements BaseDAO<Resource> {

    @Override
    public Resource create(Connection connection, Resource entity) throws Exception {
        String sql
                = "INSERT INTO resource "
                + "(name) "
                + "VALUES (?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getName());

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
    public Resource readById(Connection connection, Long id) throws Exception {
        String sql
                = "SELECT * FROM resource "
                + "WHERE id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();

        Resource resource = null;
        while (resultSet.next()) {
            resource = new Resource(resultSet.getLong("id"),
                            resultSet.getString("name"));
        }

        resultSet.close();
        ps.close();

        return resource;
    }

    @Override
    public List<Resource> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<Resource> resourceList = new ArrayList<>();
        String sql
                = "SELECT * FROM resource "
                + "WHERE 1=1";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY name";

        if (limit != null && limit > 0) {
            sql += " LIMIT " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " OFFSET " + offset;
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            resourceList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return resourceList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM resource a WHERE 1=1 ";
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
    public void update(Connection connection, Resource entity) throws Exception {
        String sql = "UPDATE resource SET name=? WHERE id=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getName());
        ps.setLong(++i, entity.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM resource WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        String infoNameEQ = (String) criteria.get(ResourceCriteria.NAME_EQ);
        if (infoNameEQ != null) {
            sql += " AND name ='" + infoNameEQ + "'";
        }
        
        String infoNameILIKE = (String) criteria.get(ResourceCriteria.NAME_ILIKE);
        if (infoNameILIKE != null) {
            sql += " AND name ILIKE '%" + infoNameILIKE + "'";
        }

        return sql;
    }

    @Override
    public Resource parser(ResultSet resultSet) throws SQLException {
        Resource resource = new Resource(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
        return resource;
    }
    
}
