/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.OSCCriteria;
import br.com.hermes.model.entitys.OSC;
import br.com.hermes.model.enums.StatusEnum;
import br.com.hermes.model.services.PublicAdministrationService;
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
public class OSCDAO implements BaseDAO<OSC>{
    
    @Override
    public OSC create(Connection connection, OSC entity) throws Exception {
        String sql
                = "INSERT INTO osc "
                + "(name, email, cnpj, phone, street, neighborhood, city, valid, status, user_fk) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getName());
            preparedStatement.setString(++indexQueryParameter, entity.getEmail());
            preparedStatement.setString(++indexQueryParameter, entity.getCnpj());
            preparedStatement.setString(++indexQueryParameter, entity.getPhone());
            preparedStatement.setString(++indexQueryParameter, entity.getStreet());
            preparedStatement.setString(++indexQueryParameter, entity.getNeighborhood());
            preparedStatement.setString(++indexQueryParameter, entity.getCity());
            preparedStatement.setBoolean(++indexQueryParameter, entity.getValid());
            preparedStatement.setString(++indexQueryParameter, entity.getStatus().toString());
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
    public OSC readById(Connection connection, Long id) throws Exception {
        String sql
                = "SELECT * FROM osc "
                + "WHERE id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();
        UserService userService = new UserService();

        OSC osc = null;
        while (resultSet.next()) {
            osc = new OSC(resultSet.getLong("id"),
                          resultSet.getString("name"),
                          resultSet.getString("email"),
                          resultSet.getString("cnpj"),
                          resultSet.getString("phone"),
                          resultSet.getString("street"),
                          resultSet.getString("neighborhood"),
                          resultSet.getString("city"),
                          resultSet.getBoolean("valid"),
                          StatusEnum.valueOf(resultSet.getString("status")),
                          userService.readById(resultSet.getLong("user_fk"))
            );
        }

        resultSet.close();
        ps.close();

        return osc;
    }

    @Override
    public List<OSC> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<OSC> oscList = new ArrayList<>();
        String sql
                = "SELECT * FROM osc "
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
            oscList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return oscList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM osc a WHERE 1=1 ";
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
    public void update(Connection connection, OSC entity) throws Exception {
        String sql = "UPDATE osc SET name=?, email=?, cnpj=?, phone=?, street=?, neighborhood=?, city=?, valid=?, status=?, user_fk=? WHERE id=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        int indexQueryParameter = 0;
        
        ps.setString(++indexQueryParameter, entity.getName());
        ps.setString(++indexQueryParameter, entity.getEmail());
        ps.setString(++indexQueryParameter, entity.getCnpj());
        ps.setString(++indexQueryParameter, entity.getPhone());
        ps.setString(++indexQueryParameter, entity.getStreet());
        ps.setString(++indexQueryParameter, entity.getNeighborhood());
        ps.setString(++indexQueryParameter, entity.getCity());
        ps.setBoolean(++indexQueryParameter, entity.getValid());
        ps.setString(++indexQueryParameter, entity.getStatus().toString());
        ps.setLong(++indexQueryParameter, entity.getUser().getId());
        ps.setLong(++indexQueryParameter, entity.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM osc WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        String infoNameEQ = (String) criteria.get(OSCCriteria.NAME_EQ);
        if (infoNameEQ != null) {
            sql += " AND name ='" + infoNameEQ + "'";
        }
        
        String infoNameILIKE = (String) criteria.get(OSCCriteria.NAME_ILIKE);
        if (infoNameILIKE != null) {
            sql += " AND name ILIKE '%" + infoNameILIKE + "'";
        }
        
        String infoEmailEQ = (String) criteria.get(OSCCriteria.EMAIL_EQ);
        if (infoEmailEQ != null) {
            sql += " AND email ='" + infoEmailEQ + "'";
        }
        
        String infoEmailILIKE = (String) criteria.get(OSCCriteria.EMAIL_ILIKE);
        if (infoEmailILIKE != null) {
            sql += " AND email ILIKE '%" + infoEmailILIKE + "'";
        }
        
        String infoCnpjEQ = (String) criteria.get(OSCCriteria.CNPJ_EQ);
        if (infoCnpjEQ != null) {
            sql += " AND cnpj ='" + infoCnpjEQ + "'";
        }
        
        String infoCnpjILIKE = (String) criteria.get(OSCCriteria.CNPJ_ILIKE);
        if (infoCnpjILIKE != null) {
            sql += " AND cnpj ILIKE '%" + infoCnpjILIKE + "'";
        }
        
        String infoCityEQ = (String) criteria.get(OSCCriteria.CITY_EQ);
        if (infoCityEQ != null) {
            sql += " AND city ='" + infoCityEQ + "'";
        }
        
        String infoCityILIKE = (String) criteria.get(OSCCriteria.CITY_ILIKE);
        if (infoCityILIKE != null) {
            sql += " AND city ILIKE '%" + infoCityILIKE + "'";
        }
        
        Boolean infoValidEQ = (Boolean) criteria.get(OSCCriteria.VALID_EQ);
        if (infoValidEQ != null) {
            sql += " AND valid = '" + infoValidEQ + "'";
        }
        
        String infoStatusEQ = (String) criteria.get(OSCCriteria.STATUS_EQ);
        if (infoStatusEQ != null) {
            sql += " AND status ='" + infoStatusEQ + "'";
        }
        
        Long infoUserEQ = (Long) criteria.get(OSCCriteria.USER_EQ);
        if (infoUserEQ != null && infoUserEQ > 0) {
            sql += " AND user_fk ='" + infoUserEQ + "'";
        }

        return sql;
    }

    @Override
    public OSC parser(ResultSet resultSet) throws SQLException {
        
        UserService userService = new UserService();
        PublicAdministrationService publicAdminService = new PublicAdministrationService();
        
        OSC osc = null;
        try {
            osc = new OSC(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("cnpj"),
                    resultSet.getString("phone"),
                    resultSet.getString("street"),
                    resultSet.getString("neighborhood"),
                    resultSet.getString("city"),
                    resultSet.getBoolean("valid"),
                    StatusEnum.valueOf(resultSet.getString("status")),
                    userService.readById(resultSet.getLong("user_fk"))
            );
        } catch (Exception ex) {
            Logger.getLogger(OSCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return osc;
    }
}
