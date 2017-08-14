/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.controllers;

import br.com.hermes.api.base.controller.BasePublicAdministrationController;
import br.com.hermes.api.dtos.inputs.PublicAdministrationForm;
import br.com.hermes.api.dtos.outputs.PublicAdministrationResponse;
import br.com.hermes.model.criterias.PublicAdministrationCriteria;
import br.com.hermes.model.entitys.PublicAdministration;
import br.com.hermes.model.services.PublicAdministrationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Brenner
 */
@RestController
@RequestMapping(value = "/publicAdmin")
public class PublicAdministrationController implements BasePublicAdministrationController {
    
    @Autowired
    private PublicAdministrationService service;
    private static final long LIMIT = 10;


    @Override
    public @ResponseBody List<PublicAdministrationResponse> readByCriteria(
            @RequestParam(required = false) Long offset,
            @RequestParam(required = false) String nome) throws Exception {

        Map<Long, Object> criteria = new HashMap<>();
        criteria.put(PublicAdministrationCriteria.NAME_EQ, nome);

        List<PublicAdministration> publicAdmList = service.readByCriteria(criteria, LIMIT, offset);
        return PublicAdministrationResponse.map(publicAdmList);
    }

    @Override
    public PublicAdministrationResponse create(@RequestBody @Validated PublicAdministrationForm publicAdmForm) throws Exception {
        PublicAdministration publicAdm = publicAdmForm.build();
        publicAdm = service.create(publicAdm);
        return new PublicAdministrationResponse(publicAdm);
    }
    
    @Override
    public void delete(@PathVariable Long id) throws Exception{
        service.delete(id);
    }
}
