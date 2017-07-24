/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.UserCriteria;
import br.com.hermes.model.entitys.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe de implementação dos métodos da interface BaseDAO responsavél pela
 * persistência da entidade de User.
 *
 * @author Diego Dulval
 * @version 1.0
 * @since Release 01
 *
 */
public class UserDAO implements BaseDAO<User> {

    @Override
    public User create(Connection connection, User entity) throws Exception {
        String sql
                = "INSERT INTO user_account "
                + "(use_name, use_password) "
                + "VALUES (?, ?) RETURNING use_id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getName());
            preparedStatement.setString(++indexQueryParameter, entity.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("use_id"));
            }

            resultSet.close();
            preparedStatement.close();
        }

        return entity;
    }

    @Override
    public User readById(Connection connection, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<User> userList = new ArrayList<>();
        String sql
                = "SELECT * FROM user_account "
                + "WHERE 1=1";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY use_name";

        if (limit != null && limit > 0) {
            sql += " LIMIT " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " OFFSET " + offset;
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            userList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return userList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM user_account u WHERE 1=1 ";
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
    public void update(Connection connection, User entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM user_account WHERE use_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        String infoNameEQ = (String) criteria.get(UserCriteria.NAME_EQ);
        if (infoNameEQ != null) {
            sql += " AND use_name ='" + infoNameEQ + "'";
        }

        String infoTitleIlike = (String) criteria.get(UserCriteria.NAME_ILIKE);
        if (infoTitleIlike != null && !infoTitleIlike.trim().isEmpty()) {
            sql += " AND use_name ILIKE '%" + infoTitleIlike + "%'";
        }

        return sql;
    }

    @Override
    public User parser(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getLong("use_id"),
                resultSet.getString("use_name"),
                resultSet.getString("use_password")
        );
        return user;
    }

}
