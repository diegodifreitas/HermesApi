/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.PublicAdministrationCriteria;
import br.com.hermes.model.entitys.PublicAdministration;
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
public class PublicAdministrationDAO implements BaseDAO<PublicAdministration> {

    @Override
    public PublicAdministration create(Connection connection, PublicAdministration entity) throws Exception {
        String sql
                = "INSERT INTO public_administration "
                + "(cnpj, name, phone, street, neighborhood, city) "
                + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getCnpj());
            preparedStatement.setString(++indexQueryParameter, entity.getName());
            preparedStatement.setString(++indexQueryParameter, entity.getPhone());
            preparedStatement.setString(++indexQueryParameter, entity.getStreet());
            preparedStatement.setString(++indexQueryParameter, entity.getNeighborhood());
            preparedStatement.setString(++indexQueryParameter, entity.getCity());

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
    public PublicAdministration readById(Connection connection, Long id) throws Exception {
        String sql
                = "SELECT * FROM public_administration "
                + "WHERE id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();

        PublicAdministration publicAdmin = null;
        while (resultSet.next()) {
            publicAdmin = new PublicAdministration(resultSet.getLong("id"),
                            resultSet.getString("cnpj"),
                            resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getString("street"),
                            resultSet.getString("neighborhood"),
                            resultSet.getString("city"));
        }

        resultSet.close();
        ps.close();

        return publicAdmin;
    }

    @Override
    public List<PublicAdministration> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<PublicAdministration> publicAdminList = new ArrayList<>();
        String sql
                = "SELECT * FROM public_administration "
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
            publicAdminList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return publicAdminList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM public_administration pa WHERE 1=1 ";
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
    public void update(Connection connection, PublicAdministration entity) throws Exception {
        String sql = "UPDATE public_administration SET cnpj=?, name=?, phone=?, street=?, neighborhood=?, city=? WHERE id=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getCnpj());
        ps.setString(++i, entity.getName());
        ps.setString(++i, entity.getPhone());
        ps.setString(++i, entity.getStreet());
        ps.setString(++i, entity.getNeighborhood());
        ps.setString(++i, entity.getCity());
        ps.setLong(++i, entity.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM public_administration WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";
        
        String infoCnpjEQ = (String) criteria.get(PublicAdministrationCriteria.CNPJ_EQ);
        if (infoCnpjEQ != null) {
            sql += " AND cnpj ='" + infoCnpjEQ + "'";
        }
        
        String infoCnpjILIKE = (String) criteria.get(PublicAdministrationCriteria.CNPJ_ILIKE);
        if (infoCnpjILIKE != null) {
            sql += " AND cnpj ILIKE '%" + infoCnpjILIKE + "'";
        }

        String infoNameEQ = (String) criteria.get(PublicAdministrationCriteria.NAME_EQ);
        if (infoNameEQ != null) {
            sql += " AND type ='" + infoNameEQ + "'";
        }
        
        String infoNameILIKE = (String) criteria.get(PublicAdministrationCriteria.NAME_ILIKE);
        if (infoNameILIKE != null) {
            sql += " AND name ILIKE '%" + infoNameILIKE + "'";
        }
        
        String infoCityEQ = (String) criteria.get(PublicAdministrationCriteria.CITY_EQ);
        if (infoCityEQ != null) {
            sql += " AND city ='" + infoCityEQ + "'";
        }
        
        String infoCityILIKE = (String) criteria.get(PublicAdministrationCriteria.CITY_ILIKE);
        if (infoCityILIKE != null) {
            sql += " AND city ILIKE '%" + infoCityILIKE + "'";
        }

        return sql;
    }

    @Override
    public PublicAdministration parser(ResultSet resultSet) throws SQLException {
        PublicAdministration publicAdmin = new PublicAdministration(
                resultSet.getLong("id"),
                resultSet.getString("cnpj"),
                resultSet.getString("name"),
                resultSet.getString("phone"),
                resultSet.getString("street"),
                resultSet.getString("neighborhood"),
                resultSet.getString("city")
        );
        return publicAdmin;
    }
    
}
