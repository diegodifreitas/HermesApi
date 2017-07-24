/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.dtos.inputs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author NPDI-04
 */
@Data
public class UploadForm {

    private String extraField;

    private MultipartFile[] files;   
}
