/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.model.entitys;

import br.com.hermes.model.base.BaseEntity;
import br.com.hermes.model.enums.DivulgationEnum;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Diego Dulval
 */

@Data
public class Divulgation extends BaseEntity {

    private String title;
    private String description;
    private Timestamp createdAt;
    private DivulgationEnum type;
    private List<File> fileList;

    public Divulgation(Long id, 
                       DivulgationEnum type, 
                       String title, 
                       String description, 
                       Timestamp createdAt) {
        super();
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }
}
