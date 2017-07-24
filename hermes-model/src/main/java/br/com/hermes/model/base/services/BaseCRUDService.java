/*
 */
package br.com.hermes.model.base.services;

import br.com.hermes.model.base.BaseEntity;
import java.util.List;
import java.util.Map;

/** 
* Interface responsavél por definir os métodos comun de serviços do sistema.
* Para implementar a interface BaseCRUDService é necessario que a classe em questão extenda BaseEntity,
* Deste modo só é possivél realizar operaçoes CRUD em Classes de Entidades.
* 
*   @author Diego Dulval
*   @version 1.0
*   @since Release 01
* 
* @param <E> é a classe generica que extende BaseEntity
*/
public interface BaseCRUDService<E extends BaseEntity>{
    /**
        * @param entity objeto da entidade que implementa a interface.
     */
    public E create(E entity) throws Exception;
    
    /**
        * @param id numero identificador do registro.
     */
    public E readById(Long id) throws Exception;

    /**
        * @param criteria mapa contendo as informações de criterios de busca.
        * @param limit quantidade maxima de registros oriundos do banco.
        * @param offset posição inicial da coleta de registros do banco.
        * @return lista de registros da classe implementada.
    */
    public List<E> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception;

    /**
        * @param criteria mapa contendo as informações de criterios de busca.
        * @return quantidade de registros com os criterios informados.
    */
    public Long countByCriteria(Map<Long, Object> criteria) throws Exception;
    
    /**
        * @param entity objeto da entidade que implementa a interface.
     */
    public void update(E entity) throws Exception;

    /**
        * @param id numero identificador do registro.
    */
    public void delete(Long id) throws Exception;
    
    /**
        * @param fields mapa contendo o paramentro e o valor.
        * @return mapa com o nome do parametro e o status.
    */
    public Map<String,String> validate(Map<String,Object> fields) throws Exception;
}
