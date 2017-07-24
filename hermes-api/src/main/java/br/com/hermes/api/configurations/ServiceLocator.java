/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.configurations;

import br.com.hermes.model.services.DivulgationService;
import br.com.hermes.model.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Classe com a responsabilidade de instanciar os serviços que vão ser
 * utilizados no controller da aplicação
 */
@Configuration
public class ServiceLocator {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public DivulgationService divulgationService() {
        return new DivulgationService();
    }
}
