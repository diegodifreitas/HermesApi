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
public class PublicServer extends BaseEntity{
    
    private String name;
    private String email;
    private String office;
    private User user;
    private PublicAdministration publicAdmin;
    
    public PublicServer(Long id, String name, String email, String Office, User user, PublicAdministration publicAdmin){
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.office = office;
        this.user = user;
        this.publicAdmin = publicAdmin;
    }
}
