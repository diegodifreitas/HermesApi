/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.dtos.inputs;

import br.com.hermes.model.entitys.PublicAdministration;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Brenner
 */
@Data
public class PublicAdministrationForm {

    @NotBlank(message = "campo cnpj obrigatorio")
    private String cnpj;
    @NotBlank(message = "campo nome obrigatorio")
    private String name;
    private String phone;
    private String street;
    private String neighborhood;
    private String city;
    
     public PublicAdministration build() {
        PublicAdministration publicAdm = new PublicAdministration(
                null,
                cnpj,
                name,
                phone,
                street,
                neighborhood,
                city
        );
        return publicAdm;
    }
}
