/*
* Código fonte  produzido  pelo  Núcleo  de  Pesquisa,  Desenvolvimento e Inovação (NPDI) da  
* FAI - Centro de Ensino Superior em Gestão, Tecnologia e Educação.
* Todos os direitos reservados à DL Comércio e Indústria de Produtos Eletrônicos, 
* através do Convênio para Capacitação, Pesquisa e Desenvolvimento de Projetos 
* assinado em 30 de setembro de 2013.
 */
package br.com.hermes.model.infra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * Classe de configuração de acesso ao Banco de Dados
 *
 * @author Diego Dulval
 * @version 1.0
 * @since Release 01
 *
 */
public class ConnectionManager {

    private PGPoolingDataSource  dataSource;
    private static ConnectionManager instance;

    public Connection getConnection() throws Exception {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    private ConnectionManager() throws FileNotFoundException, IOException {

        dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("BD_HS_Teste");
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("bd_hs_teste");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setMaxConnections(30);
        dataSource.setInitialConnections(2);
    }

    public static ConnectionManager getInstance() throws IOException {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
}
