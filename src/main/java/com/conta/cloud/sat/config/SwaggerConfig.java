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
	
	public static final String TAG_CP = "Codigo_Postal";
	public static final String TAG_ADUANA = "Aduanas";
	public static final String TAG_ESTADO = "Entidades_Federativas";
        public static final String TAG_MUNICIPIO = "Municipios";
        public static final String TAG_PAIS = "Paises";
        public static final String TAG_UNIDAD = "Unidades de Medida";
	public static final String TAG_PRODUCTO = "Productos";
	public static final String TAG_FORMA_PAGO = "Formas de Pago";
        public static final String TAG_IMPUESTOS = "Impuestos";
        public static final String TAG_MONEDA = "Monedas";
        public static final String TAG_METODO_PAGO = "Metodos de Pago";
        public static final String TAG_NO_PEDIMENTO_ADUANAL = "Numero Pedimento Aduanal";
        public static final String TAG_PATENTE_ADUANAL = "Patente Aduanal";
        public static final String TAG_REGIMEN_FISCAL = "Regimen Fiscal";
        public static final String TAG_TASA_CUOTA = "Tasa Cuota";
        public static final String TAG_TIPO_COMPROBANTE = "Tipo de Comprobante";
        public static final String TAG_TIPO_RELACION = "Tipo de Relacion";
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
                .tags(new Tag(TAG_CP, "Servicios REST para obtener códigos postales"))
                .tags(new Tag(TAG_ESTADO, "Servicios REST para obtener Entidades Federativas"))
                .tags(new Tag(TAG_MUNICIPIO, "Servicios REST para obtener Municipios"))
                .tags(new Tag(TAG_ADUANA, "Servicios REST para obtener Aduanas"))
                .tags(new Tag(TAG_PAIS, "Servicios REST para obtener paises"))
                .tags(new Tag(TAG_UNIDAD, "Servicios REST para obtener unidades de medida"))
		.tags(new Tag(TAG_PRODUCTO, "Servicio REST para obtener productos"))
		.tags(new Tag(TAG_FORMA_PAGO, "Servicio REST para obtener Formas de Pago"))
                .tags(new Tag(TAG_IMPUESTOS, "Servicio REST para obtener Impuestos"))
                .tags(new Tag(TAG_MONEDA, "Servicio REST para obtener Monedas"))
                .tags(new Tag(TAG_METODO_PAGO, "Servicio REST para obtener metodos de Pago"))
                .tags(new Tag(TAG_NO_PEDIMENTO_ADUANAL, "Servicio REST para obtener numeros de pedimento aduanal"))
                .tags(new Tag(TAG_PATENTE_ADUANAL, "Servicio REST para obtener patentes aduanales"))
                .tags(new Tag(TAG_REGIMEN_FISCAL, "Servicio REST para obtener Regimenes Fiscales"), //
                        new Tag(TAG_TASA_CUOTA, "Servicio REST para obtener Tasa/Cuota"), //
                        new Tag(TAG_TIPO_COMPROBANTE, "Servicio REST para obtener tipos de comprobante"), //
                        new Tag(TAG_TIPO_RELACION, "Servicio REST para obtener tipos de relacion")
                );
             
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
