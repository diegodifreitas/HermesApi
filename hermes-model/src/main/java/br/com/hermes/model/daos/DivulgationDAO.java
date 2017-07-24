/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.DivulgationCriteria;
import br.com.hermes.model.entitys.Divulgation;
import br.com.hermes.model.enums.DivulgationEnum;
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
 * persistência da entidade de Divulgação.
 *
 * @author Diego Dulval
 * @version 1.0
 * @since Release 01
 *
 */
public class DivulgationDAO implements BaseDAO<Divulgation> {

    @Override
    public Divulgation create(Connection connection, Divulgation entity) throws Exception {
        String sql
                = "INSERT INTO divulgation "
                + "(div_type, div_title, div_description, div_created_at) "
                + "VALUES (?, ?, ?, ?) RETURNING div_id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getType().toString());
            preparedStatement.setString(++indexQueryParameter, entity.getTitle());
            preparedStatement.setString(++indexQueryParameter, entity.getDescription());
            preparedStatement.setTimestamp(++indexQueryParameter, entity.getCreatedAt());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("div_id"));
            }

            resultSet.close();
            preparedStatement.close();
        }

        return entity;
    }

    @Override
    public Divulgation readById(Connection connection, Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Divulgation> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<Divulgation> divulgationList = new ArrayList<>();
        String sql
                = "SELECT * FROM divulgation d "
                + "WHERE 1=1";

        if (criteria != null) {
            sql += applyCriteria(criteria);
        }

        sql += " ORDER BY d.div_created_at";

        if (limit != null && limit > 0) {
            sql += " LIMIT " + limit;
        }
        if (offset != null && offset >= 0) {
            sql += " OFFSET " + offset;
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            divulgationList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return divulgationList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM divulgation d WHERE 1=1 ";
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
    public void update(Connection connection, Divulgation entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM divulgation WHERE div_id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        String infoTitleEQ = (String) criteria.get(DivulgationCriteria.TITLE_EQ);
        if (infoTitleEQ != null) {
            sql += " AND d.div_title ='" + infoTitleEQ + "'";
        }

        String infoTitleIlike = (String) criteria.get(DivulgationCriteria.TITLE_ILIKE);
        if (infoTitleIlike != null && !infoTitleIlike.trim().isEmpty()) {
            sql += " AND d.div_title ILIKE '%" + infoTitleIlike + "%'";
        }

        String infoTipo = (String) criteria.get(DivulgationCriteria.TYPE_EQ);
        if (infoTipo != null) {
            switch (infoTipo.toLowerCase()) {
                case "event":
                    sql += " AND d.div_type = '" + DivulgationEnum.event.toString() + "' ";
                    break;
                case "notice":
                    sql += " AND d.div_type = '" + DivulgationEnum.notice.toString() + "' ";
                    break;
                default:
                    sql += " ";
                    break;
            }
        }
        return sql;
    }

    @Override
    public Divulgation parser(ResultSet resultSet) throws SQLException {
        Divulgation divulgation = new Divulgation(
                resultSet.getLong("div_id"),
                DivulgationEnum.valueOf(resultSet.getString("div_type")),
                resultSet.getString("div_title"),
                resultSet.getString("div_description"),
                resultSet.getTimestamp("div_created_at")
        );
        return divulgation;
    }

}
