package com.conta.cloud.sat.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.EstadoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.rest.exception.ValidationMessageConstants;
import com.conta.cloud.sat.service.EstadoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("direcciones/estados")
@Api(tags = {SwaggerConfig.TAG_ESTADO}, produces = SwaggerConfig.APPLICATION_JSON)
public class EstadosController {

	private final EstadoService estadoService;
	
	public EstadosController(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	@ApiOperation(value = "Obtener catálogo de Estados",httpMethod = "GET", response = Collection.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpServletResponse.SC_OK, message = WebAppConstants.SUCCESS_MESSAGE),
		@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE, response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE, response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE),
		@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE),
	})
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Collection<EstadoDTO>> getEstados(
			@RequestParam(required = false)
			@Pattern(regexp = "[A-Z]+", message = ValidationMessageConstants.INVALID_PAIS)
			String idPais
			) throws CatalogException{
		Collection<EstadoDTO> collection = null;
		
		if(StringUtils.isEmpty(idPais)) {
			collection = estadoService.findAll();
		} else {
			collection = estadoService.findByPais(idPais);
		}
		
		return new ResponseEntity<Collection<EstadoDTO>>(collection, HttpStatus.OK);
	}
}
