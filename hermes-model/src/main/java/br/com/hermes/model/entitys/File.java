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
public class File extends BaseEntity{
    private String nome;
    private String url;
    private Long onwer;
    
}
