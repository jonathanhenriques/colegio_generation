package com.jonathan.colegiogeneration.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Colégio Generation")
                        .description(" API Desenvolvida para triagem do bootcamp Generation aws.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Jonathan Henrique da Silva")
                                .url("https://github.com/jonathanhenriques/colegio_generation.git")
                                .email("jonas.henrique1369@gmail.com"))
                        .license(new License()
                                .name("Licença MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação Externa")
                        .url("https://github.com/jonathanhenriques/colegio_generation.git"));
    }
}






