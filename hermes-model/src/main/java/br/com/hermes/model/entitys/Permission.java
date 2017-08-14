/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.entitys;

import br.com.hermes.model.base.BaseEntity;
import br.com.hermes.model.enums.PrivilegeEnum;
import java.sql.Timestamp;
import lombok.Data;

/**
 *
 * @author Braian
 */
@Data
public class Permission extends BaseEntity{
    
    private User user;
    private Resource resource;
    private Timestamp date;
    private PrivilegeEnum privilege;
    
    public Permission(Long id, User user, Resource resource, Timestamp date, PrivilegeEnum privilege){
        super();
        this.id = id;
        this.user = user;
        this.resource = resource;
        this.date = date;
        this.privilege = privilege;
    }
}
