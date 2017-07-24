/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.controllers;

import br.com.hermes.api.dtos.inputs.DivulgationForm;
import br.com.hermes.api.dtos.outputs.DivulgationResponse;
import br.com.hermes.model.entitys.Divulgation;
import br.com.hermes.model.services.DivulgationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Diego Dulval
 */
@RestController
@RequestMapping(value = "/divulgation")
public class DivulgationController {

    @Autowired
    private DivulgationService divulgationService;
    private static final long LIMIT = 10;

    @ApiOperation(value = "Obtém todas as Divulgações cadastradas.")
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody List<DivulgationResponse> read() throws Exception {
        List<Divulgation> divulgation = null;
        divulgation = divulgationService.readByCriteria(null, LIMIT, null);
        return DivulgationResponse.map(divulgation);
    }

    @ApiOperation(value = "Cadastra uma nova Divulgação.",
            response = DivulgationResponse.class,
            authorizations = {
                @Authorization(value = "oauth2", scopes = @AuthorizationScope(scope = "entity.create", description = "Criação de uma Divulgação na API"))
            }
    )
    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DivulgationResponse create(@RequestBody DivulgationForm divulgationForm) throws Exception {
        Divulgation divulgation = divulgationForm.build();
        divulgation = divulgationService.create(divulgation);
        return new DivulgationResponse(divulgation);
    }

}
