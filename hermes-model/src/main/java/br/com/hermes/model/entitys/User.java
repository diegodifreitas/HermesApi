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
 * @author Diego Dulval
 */
@Data
public class User extends BaseEntity{
    
    private String password;
    private String type;
    private String status;

    public User(long id, String password, String type, String status) {
        super();
        this.id = id;
        this.password = password;
        this.type = type;
        this.status = status;
    }
}
