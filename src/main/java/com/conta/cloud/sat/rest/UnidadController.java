package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.service.UnidadService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = SwaggerConfig.UNIDAD_TAG)
@RestController
@RequestMapping("unidades")
public class UnidadController {

    private final UnidadService unidadService;

    public UnidadController(UnidadService service) {
        this.unidadService = service;
    }


    @ApiOperation(value = "Obtener codigos unidades de medida",httpMethod = "GET", response = Collection.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa"),
		@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Error en parametros enviados", response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar peticion", response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Accesso denegado"),
		@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida"),
	})
    @GetMapping
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<Collection<UnidadDTO>> findUnidades( //
        @ApiParam(name = "nombre", value = "Nombre de la unidad a buscar") //
        @RequestParam(required=false)String nombre, //
        @ApiParam(name = "simbolo", value = "Símbolo de la unidad a buscar") //
        @RequestParam(required=false)String simbolo, //
        @ApiParam(name = "fechaInicio", value = "Fecha de inicial")
        @RequestParam(required=false)String fechaInicio
    ) throws CatalogException {
        return new ResponseEntity<Collection<UnidadDTO>>(
            unidadService.findUnidades(nombre, simbolo, fechaInicio),
            HttpStatus.OK
        );
    }
}