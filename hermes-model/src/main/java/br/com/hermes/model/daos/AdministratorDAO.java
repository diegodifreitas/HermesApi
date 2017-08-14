/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.AdministratorCriteria;
import br.com.hermes.model.entitys.Administrator;
import br.com.hermes.model.services.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Braian
 */
public class AdministratorDAO implements BaseDAO<Administrator> {

    @Override
    public Administrator create(Connection connection, Administrator entity) throws Exception {
        String sql
                = "INSERT INTO administrator "
                + "(name, email, user_fk) "
                + "VALUES (?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getName());
            preparedStatement.setString(++indexQueryParameter, entity.getEmail());
            preparedStatement.setLong(++indexQueryParameter, entity.getUser().getId());

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
    public Administrator readById(Connection connection, Long id) throws Exception {
        String sql
                = "SELECT * FROM administrator "
                + "WHERE id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();
        UserService userService = new UserService();

        Administrator admin = null;
        while (resultSet.next()) {
            admin = new Administrator(resultSet.getLong("id"),
                                      resultSet.getString("name"),
                                      resultSet.getString("email"),
                                      userService.readById(resultSet.getLong("user_fk")));
        }

        resultSet.close();
        ps.close();

        return admin;
    }

    @Override
    public List<Administrator> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<Administrator> adminList = new ArrayList<>();
        String sql
                = "SELECT * FROM administrator "
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
            adminList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return adminList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM administrator a WHERE 1=1 ";
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
    public void update(Connection connection, Administrator entity) throws Exception {
        String sql = "UPDATE administrator SET name=?, email=?, user_fk=? WHERE id=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getName());
        ps.setString(++i, entity.getEmail());
        ps.setLong(++i, entity.getUser().getId());
        ps.setLong(++i, entity.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM administrator WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        String infoNameEQ = (String) criteria.get(AdministratorCriteria.NAME_EQ);
        if (infoNameEQ != null) {
            sql += " AND name ='" + infoNameEQ + "'";
        }
        
        String infoNameILIKE = (String) criteria.get(AdministratorCriteria.NAME_ILIKE);
        if (infoNameILIKE != null) {
            sql += " AND name ILIKE '%" + infoNameILIKE + "'";
        }
        
        String infoEmailEQ = (String) criteria.get(AdministratorCriteria.EMAIL_EQ);
        if (infoEmailEQ != null) {
            sql += " AND email ='" + infoEmailEQ + "'";
        }
        
        String infoEmailILIKE = (String) criteria.get(AdministratorCriteria.EMAIL_ILIKE);
        if (infoEmailILIKE != null) {
            sql += " AND email ILIKE '%" + infoEmailILIKE + "'";
        }
        
        Long infoUserEQ = (Long) criteria.get(AdministratorCriteria.USER_EQ);
        if (infoUserEQ != null && infoUserEQ > 0) {
            sql += " AND user_fk ='" + infoUserEQ + "'";
        }

        return sql;
    }

    @Override
    public Administrator parser(ResultSet resultSet) throws SQLException {
        
        UserService userService = new UserService();
        
        Administrator admin = null;
        try {
            admin = new Administrator(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    userService.readById(resultSet.getLong("user_fk"))
            );
        } catch (Exception ex) {
            Logger.getLogger(AdministratorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return admin;
    }
    
}
