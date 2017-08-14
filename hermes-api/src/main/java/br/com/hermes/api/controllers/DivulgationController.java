/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.controllers;

import br.com.hermes.api.base.controller.BaseDivulgacaoController;
import br.com.hermes.api.dtos.inputs.DivulgationForm;
import br.com.hermes.api.dtos.outputs.DivulgationResponse;
import br.com.hermes.model.entitys.Divulgation;
import br.com.hermes.model.services.DivulgationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
public class DivulgationController implements BaseDivulgacaoController {

    @Autowired
    private DivulgationService divulgationService;
    private static final long LIMIT = 10;

    /**
     * GET / divulgacao
     */
    @Override
    public @ResponseBody List<DivulgationResponse> read() throws Exception {
        List<Divulgation> divulgation = divulgationService.readByCriteria(null, LIMIT, null);
        return DivulgationResponse.map(divulgation);
    }

    /**
     * POST / divulgacao
     */
    public DivulgationResponse create(@RequestBody @Validated DivulgationForm divulgationForm) throws Exception {
        Divulgation divulgation = divulgationForm.build();
        divulgation = divulgationService.create(divulgation);
        return new DivulgationResponse(divulgation);
    }

}
