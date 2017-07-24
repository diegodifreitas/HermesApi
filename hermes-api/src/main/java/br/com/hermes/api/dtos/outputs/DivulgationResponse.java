/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.dtos.outputs;

import br.com.hermes.model.entitys.Divulgation;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 *
 * @author Diego Dulval
 */
@Data
public class DivulgationResponse {

    private Long id;
    private String title;
    private String description;
    private Timestamp createdAt;
    private String type;
    
    

    public DivulgationResponse(Divulgation divulgation) {
        this.id = divulgation.getId();
        this.type = divulgation.getType().toString();
        this.title = divulgation.getTitle();
        this.description = divulgation.getDescription();
        this.createdAt = divulgation.getCreatedAt();

    }

    public static List<DivulgationResponse> map(List<Divulgation> divulgations) {
        return divulgations.stream().map(DivulgationResponse::new).collect(Collectors.toList());
    }
}
