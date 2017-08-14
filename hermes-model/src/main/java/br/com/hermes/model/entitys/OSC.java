/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.entitys;

import br.com.hermes.model.base.BaseEntity;
import br.com.hermes.model.enums.StatusEnum;
import lombok.Data;

/**
 *
 * @author Braian
 */
@Data
public class OSC extends BaseEntity{
    
    private String name;
    private String email;
    private String cnpj;
    private String phone;
    private String street;
    private String neighborhood;
    private String city;
    private Boolean valid;
    private StatusEnum status;
    private User user;
    
    public OSC(Long id, String name, String email, String cnpj, String phone, String street, String neighborhood, String city, Boolean valid, StatusEnum status, User user){
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
        this.phone = phone;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.valid = valid;
        this.status = status;
        this.user = user;
    }
}
