/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.dtos.outputs;

import br.com.hermes.model.entitys.PublicAdministration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 *
 * @author Brenner
 */
@Data
public class PublicAdministrationResponse {
    private Long id;
    private String cnpj;
    private String name;
    private String phone;
    private String street;
    private String neighborhood;
    private String city;

    public PublicAdministrationResponse(PublicAdministration publicAdm) {
        this.id = publicAdm.getId();
        this.cnpj = publicAdm.getCnpj();
        this.name = publicAdm.getName();
        this.phone = publicAdm.getPhone();
        this.street = publicAdm.getStreet();
        this.neighborhood = publicAdm.getNeighborhood();
        this.city = publicAdm.getCity();
    }

    public static List<PublicAdministrationResponse> map(List<PublicAdministration> publicAdm) {
        return publicAdm.stream().map(PublicAdministrationResponse::new).collect(Collectors.toList());
    }
}
