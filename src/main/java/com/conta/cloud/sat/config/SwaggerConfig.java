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
	
	public static final String CP_TAG = "Codigo_Postal";
	public static final String ADUANA_TAG = "Aduanas";
	public static final String ESTADO_TAG = "Entidades_Federativas";
        public static final String MUNICIPIO_TAG = "Municipios";
        public static final String PAIS_TAG = "Paises";
        public static final String UNIDAD_TAG = "Unidades de Medida";
	public static final String PRODUCTO_TAG = "Productos";
        public static final String APPLICATION_JSON = "application/json";
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
                .tags(new Tag(ESTADO_TAG, "Servicios REST para obtener Entidades Federativas"))
                .tags(new Tag(MUNICIPIO_TAG, "Servicios REST para obtener Municipios"))
                .tags(new Tag(ADUANA_TAG, "Servicios REST para obtener Aduanas"))
                .tags(new Tag(PAIS_TAG, "Servicios REST para obtener paises"))
                .tags(new Tag(UNIDAD_TAG, "Servicios REST para obtener unidades de medida"))
		.tags(new Tag(PRODUCTO_TAG, "Servicio REST para obtener productos"))
                ;
             
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
