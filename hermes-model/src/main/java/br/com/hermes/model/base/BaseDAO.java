/*
 */
package br.com.hermes.model.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Interface que define os métodos comum de acesso aos registros no banco de dados.
 * 
 * @author Diego Dulval
 * @version 1.0
 * @since Release 01
 * 
 * @param <E> classe generica que restrigi a implementação da classe apenas para classes filhas de BaseEntity.
 */
public interface BaseDAO<E extends BaseEntity> {
        /**
        * @param connection objeto de conexão com o banco de dados.
        * @param entity objeto da entidade que implementa a interface.
        */
        public E create(Connection connection, E entity) throws Exception;

        /**
        * @param connection objeto de conexão com o banco de dados.
        * @param id numero inteiro de indetificação do registro.
        */
        public E readById(Connection connection, Long id) throws Exception;

        /**
        * @param connection objeto de conexão com o banco de dados.
        * @param criteria mapa contendo as informações de criterios de busca.
        * @param limit quantidade maxima de registros oriundos do banco.
        * @param offset posição inicial da coleta de registros do banco.
        * @return lista de registros da classe implementada.
        */
        public List<E> readByCriteria(Connection connection, Map<Long, Object> criteria, Long limit, Long offset) throws Exception;

        /**
        * @param connection objeto de conexão com o banco de dados.
        * @param criteria mapa contendo as informações de criterios de busca.
        * @return quantidade de registros com os criterios informados.
        */
        public Long countByCriteria(Connection connection, Map<Long, Object> criteria) throws Exception;

        /**
        * @param connection objeto de conexão com o banco de dados.
        * @param entity objeto da entidade que implementa a interface.
        */
        public void update(Connection connection, E entity) throws Exception;

        /**
        * @param connection objeto de conexão com o banco de dados.
        * @param id numero inteiro de indetificação do registro.
        */
        public void delete(Connection connection, Long id) throws Exception;
        
        /**
        * @param criteria mapa contendo as informações de criterios de busca.
        * @return condição where para consulta ao banco de dados.
        */
        public String applyCriteria(Map<Long, Object> criteria);
        
        /**
        * @param resultSet resultado da consulta realizada no banco de dados.
        * @return Entidade preenchida com os registros oriundos do banco de dados.
        */
        public E parser(ResultSet resultSet) throws SQLException;
}
