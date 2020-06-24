package com.conta.cloud.sat.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.CodigoPostalDTO;
import com.conta.cloud.sat.service.CodigoPostalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("direcciones/codigos/postales")
@Validated
@Api(produces = "application/json", tags = SwaggerConfig.CP_TAG)
public class CodigoPostalController {

	private final CodigoPostalService codigoPostalService;
	
	public CodigoPostalController(CodigoPostalService codigoPostalService) {
		this.codigoPostalService = codigoPostalService;
	}
	
	@ApiOperation(value = "Obtener codigos postales",httpMethod = "GET", response = Collection.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa"),
		@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Error en parametros enviados", response = ErrorWrapperResponse.class),
		@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar peticion", response = ErrorWrapperResponse.class),
		@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Accesso denegado"),
		@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida"),
	})
	@GetMapping
	@PreAuthorize(value = "isAuthenticated()")
	public ResponseEntity<Collection<CodigoPostalDTO>> findCodigo(
			@RequestParam()
			@Pattern(message = "Parametro no valido para estado", regexp = "[A-Z]+")
			String idEstado,
			@RequestParam(required = false)
			@Pattern(message = "Parametro no valido para municipio", regexp = "[0-9]+")
			String idMunicipio,
			@RequestParam()
			@Pattern(message = "Parametro no valido para codigo postal", regexp = "[0-9]+")
			String cp) throws Exception{
		Collection<CodigoPostalDTO> dtos = this.codigoPostalService.findCodigoPostal(idEstado, idMunicipio, cp);
		return new ResponseEntity<Collection<CodigoPostalDTO>>(dtos, HttpStatus.OK);
	}
}
