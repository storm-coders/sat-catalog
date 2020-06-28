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
import com.conta.cloud.sat.dto.MunicipioDTO;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.rest.exception.ValidationMessageConstants;
import com.conta.cloud.sat.service.MunicipiosService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("direcciones/municipios")
@Api(produces = "application/json", tags = SwaggerConfig.MUNICIPIO_TAG)
@Validated
public class MunicipioController {

	private final MunicipiosService municipiosService;
	
	public MunicipioController(MunicipiosService municipiosService) {
		this.municipiosService = municipiosService;
	}
	
	@ApiOperation(value = "Obtener catalogo de Municipios",httpMethod = "GET", response = Collection.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa"),
		@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Error en parametros enviados", response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar peticion", response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Accesso denegado"),
		@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida"),
	})
	@GetMapping
	@PreAuthorize("isAuthenticated")
	public ResponseEntity<Collection<MunicipioDTO>> findMunicipios(
			@RequestParam(required = false)
			@ApiParam(name = "idEstado", value = "Id de Estado si se require filtrar")
			@Pattern(regexp = "[A-Z]+", message = ValidationMessageConstants.INVALID_ID_ESTADO)
			String idEstado
			) throws Exception {
		return new ResponseEntity<Collection<MunicipioDTO>>(
				municipiosService.findMunicipios(idEstado),
				HttpStatus.OK
				);
	}
}
