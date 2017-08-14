/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.dtos.inputs;

import br.com.hermes.model.entitys.Divulgation;
import br.com.hermes.model.enums.DivulgationEnum;
import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Diego Dulval
 */
@Data
public class DivulgationForm {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank(message = "campo type obrigatorio")
    private String type;

    public Divulgation build() {
        Divulgation divulgation = new Divulgation(
                null,
                parseType(type),
                title,
                description,
                new Timestamp(System.currentTimeMillis())
        );
        return divulgation;
    }

    public DivulgationEnum parseType(String type) {
        DivulgationEnum typeEnun = null;
        if (type != null && !type.isEmpty()) {
            switch (type.toLowerCase()) {
                case "notice":
                    typeEnun = DivulgationEnum.notice;
                    break;
                case "event":
                    typeEnun = DivulgationEnum.event;
                    break;
            }
        }
        return typeEnun;
    }
}
