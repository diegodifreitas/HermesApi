/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hermes.api.configurations;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.*;
import static com.google.common.collect.Lists.newArrayList;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import static springfox.documentation.builders.PathSelectors.*;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityScheme;

/**
 *
 * @author Diego Dulval
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    private String swaggerOAuthUrl = "http://localhost:8084/HS_WEB/oauth/token";

    @Bean
    public Docket swaggerSpringMvcPlugin() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("hermes-api")
                .select()
                .paths(paths())
                .build()
                .directModelSubstitute(Timestamp.class, String.class)
                .apiInfo(apiInfo())
                .securitySchemes(newArrayList(oauth2()));
    }

    private Predicate<String> paths() {
        return or(regex("/publicAdmin/.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HERMES - API")
                .description("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum "
                        + "has been the industry's standard dummy text ever since the 1500s, when an unknown printer "
                        + "took a "
                        + "galley of type and scrambled it to make a type specimen book. It has survived not only five "
                        + "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "
                        + "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum "
                        + "passages, and more recently with desktop publishing software like Aldus PageMaker including "
                        + "versions of Lorem Ipsum.")
                .termsOfServiceUrl("http://www.fai-mg.br")
                .license("Projeto Acadêmico")
                .licenseUrl("http://www.fai-mg.br")
                .version("1.0")
                .build();
    }

    @Bean
    SecurityScheme oauth2() {
        return new OAuthBuilder()
                .name("oauth2")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        scopes.add(new AuthorizationScope("entity.create", "Acesso para criação de uma nova entidade na API"));
        scopes.add(new AuthorizationScope("entity.read", "Acesso para leitura de entidades na API"));
        scopes.add(new AuthorizationScope("entity.put", "Acesso para alteração de entidades na API"));
        scopes.add(new AuthorizationScope("entity.delete", "Acesso para deletar entidades existente na API"));
        return scopes;
    }

    List<GrantType> grantTypes() {
        ResourceOwnerPasswordCredentialsGrant passowordGrant = new ResourceOwnerPasswordCredentialsGrant(swaggerOAuthUrl);
        return newArrayList(passowordGrant);
    }

}
