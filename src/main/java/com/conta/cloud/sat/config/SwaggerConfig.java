package com.conta.cloud.sat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;


@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	public static final String CP_TAG = "Código Postal";
	public static final String ADUANA_TAG = "Catálogo de Aduanas";
	private final Contact contact = new Contact("Victor de la Cruz",
			"https://www.codementor.io/@vcg_cruz", ""); 
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 
                .apis(RequestHandlerSelectors.basePackage("com.conta.cloud.sat.rest"))
                .paths(any())
                .build()
                .apiInfo(metaData())
                .tags(new Tag(CP_TAG, "Servicios REST para obtener códigos postales"))
                .tags(new Tag(ADUANA_TAG, "Servicios REST para obtener Aduanas"));
             
	}
	
	private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Catálogos CFDI SAT REST API")
                .description("\"Catálogos para facturación CFDI 3.3 REST API\"")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(contact)
                .build();
	}

}
