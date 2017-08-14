/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.base.controller;

import br.com.hermes.api.dtos.inputs.PublicAdministrationForm;
import br.com.hermes.api.dtos.outputs.PublicAdministrationResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Brenner
 */
public interface BasePublicAdministrationController {

    @ApiOperation(value = "Obtém todas as Administraçao Publica cadastradas.")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<PublicAdministrationResponse> readByCriteria(@RequestParam Long offset, @RequestParam String nome) throws Exception;

    @ApiOperation(value = "Cadastra uma nova Administraçao Publica.")
    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PublicAdministrationResponse create(@RequestBody PublicAdministrationForm publiAdmForm) throws Exception;

    @ApiOperation(value = "Deleta uma Administraçao Publica com o id especificado.")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) throws Exception;

}
