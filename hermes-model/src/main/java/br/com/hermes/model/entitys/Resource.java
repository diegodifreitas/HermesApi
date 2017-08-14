/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.entitys;

import br.com.hermes.model.base.BaseEntity;
import lombok.Data;

/**
 *
 * @author Braian
 */
@Data
public class Resource extends BaseEntity{
    
    private String name;
    
    public Resource(Long id, String name){
        super();
        this.id = id;
        this.name = name;
    }
}
