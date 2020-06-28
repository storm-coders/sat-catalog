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
import com.conta.cloud.sat.dto.AduanaDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.rest.exception.ValidationMessageConstants;
import com.conta.cloud.sat.service.AduanaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("aduanas")
@Validated
@Api(produces = "application/json", tags = SwaggerConfig.ADUANA_TAG)
public class AduanaController {
	
	private final AduanaService aduanaService;
	
	public AduanaController(AduanaService aduanaService) {
		this.aduanaService = aduanaService;
	}
	
	@ApiOperation(value = "Obtener catálogo de aduanas", httpMethod = "GET", response = Collection.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Error en parametros enviados", response = ApiError.class),
			@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar peticion", response = ApiError.class),
			@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Accesso denegado"),
			@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida"),
		})
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping
	public ResponseEntity<Collection<AduanaDTO>> getAduanas(
			@RequestParam()
			@Pattern(regexp = "[a-zA-Z]+", message = ValidationMessageConstants.INVALID_DESC)
			String descripcion
			) throws CatalogException{
		return new ResponseEntity<Collection<AduanaDTO>>(aduanaService.findAduanas(descripcion), HttpStatus.OK);
	}
}
