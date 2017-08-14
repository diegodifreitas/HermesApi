/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.daos;

import br.com.hermes.model.base.BaseDAO;
import br.com.hermes.model.criterias.PublicServerCriteria;
import br.com.hermes.model.entitys.PublicServer;
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
public class PublicServerDAO implements BaseDAO<PublicServer>{
    
    @Override
    public PublicServer create(Connection connection, PublicServer entity) throws Exception {
        String sql
                = "INSERT INTO public_server "
                + "(name, email, office, user_fk, public_administration_fk) "
                + "VALUES (?, ?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int indexQueryParameter = 0;

            preparedStatement.setString(++indexQueryParameter, entity.getName());
            preparedStatement.setString(++indexQueryParameter, entity.getEmail());
            preparedStatement.setString(++indexQueryParameter, entity.getOffice());
            preparedStatement.setLong(++indexQueryParameter, entity.getUser().getId());
            preparedStatement.setLong(++indexQueryParameter, entity.getPublicAdmin().getId());

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
    public PublicServer readById(Connection connection, Long id) throws Exception {
        String sql
                = "SELECT * FROM public_server "
                + "WHERE id=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();
        UserService userService = new UserService();
        PublicAdministrationService publicAdminService = new PublicAdministrationService();

        PublicServer publicServer = null;
        while (resultSet.next()) {
            publicServer = new PublicServer(resultSet.getLong("id"),
                                            resultSet.getString("name"),
                                            resultSet.getString("email"),
                                            resultSet.getString("office"),
                                            userService.readById(resultSet.getLong("user_fk")),
                                            publicAdminService.readById(resultSet.getLong("public_administration_fk"))
            );
        }

        resultSet.close();
        ps.close();

        return publicServer;
    }

    @Override
    public List<PublicServer> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        List<PublicServer> publicServerList = new ArrayList<>();
        String sql
                = "SELECT * FROM public_server "
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
            publicServerList.add(parser(resultSet));
        }

        resultSet.close();
        statement.close();

        return publicServerList;
    }

    @Override
    public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception {
        String sql = "SELECT count(*) count FROM public_server a WHERE 1=1 ";
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
    public void update(Connection connection, PublicServer entity) throws Exception {
        String sql = "UPDATE public_server SET name=?, email=?, office=?, user_fk=?, public_administration_fk=? WHERE id=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        int i = 0;
        ps.setString(++i, entity.getName());
        ps.setString(++i, entity.getEmail());
        ps.setString(++i, entity.getOffice());
        ps.setLong(++i, entity.getUser().getId());
        ps.setLong(++i, entity.getPublicAdmin().getId());
        ps.setLong(++i, entity.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(Connection connection, Long id) throws Exception {
        String sql = "DELETE FROM public_server WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public String applyCriteria(Map<Long, Object> criteria) {
        String sql = "";

        String infoNameEQ = (String) criteria.get(PublicServerCriteria.NAME_EQ);
        if (infoNameEQ != null) {
            sql += " AND name ='" + infoNameEQ + "'";
        }
        
        String infoNameILIKE = (String) criteria.get(PublicServerCriteria.NAME_ILIKE);
        if (infoNameILIKE != null) {
            sql += " AND name ILIKE '%" + infoNameILIKE + "'";
        }
        
        String infoEmailEQ = (String) criteria.get(PublicServerCriteria.EMAIL_EQ);
        if (infoEmailEQ != null) {
            sql += " AND email ='" + infoEmailEQ + "'";
        }
        
        String infoEmailILIKE = (String) criteria.get(PublicServerCriteria.EMAIL_ILIKE);
        if (infoEmailILIKE != null) {
            sql += " AND email ILIKE '%" + infoEmailILIKE + "'";
        }
        
        String infoOfficeEQ = (String) criteria.get(PublicServerCriteria.OFFICE_EQ);
        if (infoOfficeEQ != null) {
            sql += " AND office ='" + infoOfficeEQ + "'";
        }
        
        String infoOfficeILIKE = (String) criteria.get(PublicServerCriteria.OFFICE_ILIKE);
        if (infoOfficeILIKE != null) {
            sql += " AND office ILIKE '%" + infoOfficeILIKE + "'";
        }
        
        Long infoUserEQ = (Long) criteria.get(PublicServerCriteria.USER_EQ);
        if (infoUserEQ != null && infoUserEQ > 0) {
            sql += " AND user_fk ='" + infoUserEQ + "'";
        }
        
        Long infoPublicAdminEQ = (Long) criteria.get(PublicServerCriteria.PUBLIC_ADMIN_EQ);
        if (infoPublicAdminEQ != null && infoPublicAdminEQ > 0) {
            sql += " AND public_administration_fk ='" + infoPublicAdminEQ + "'";
        }

        return sql;
    }

    @Override
    public PublicServer parser(ResultSet resultSet) throws SQLException {
        
        UserService userService = new UserService();
        PublicAdministrationService publicAdminService = new PublicAdministrationService();
        
        PublicServer publicServer = null;
        try {
            publicServer = new PublicServer(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("office"),
                    userService.readById(resultSet.getLong("user_fk")),
                    publicAdminService.readById(resultSet.getLong("public_administration_fk"))
            );
        } catch (Exception ex) {
            Logger.getLogger(PublicServerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicServer;
    }
}
