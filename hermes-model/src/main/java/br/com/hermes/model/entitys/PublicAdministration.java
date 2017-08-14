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
public class PublicAdministration extends BaseEntity {
   
    private String cnpj;
    private String name;
    private String phone;
    private String street;
    private String neighborhood;
    private String city;
    
    public PublicAdministration(Long id, String cnpj, String name, String phone, String street, String neighborhood, String city){
        super();
        this.id = id;
        this.cnpj = cnpj;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
    }
}
