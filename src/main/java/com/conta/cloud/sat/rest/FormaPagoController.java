package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.FormaPagoDTO;
import com.conta.cloud.sat.service.FormaPagoService;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.exception.CatalogException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletResponse;

import java.util.Collection;

@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = SwaggerConfig.FORMA_PAGO)
@RestController()
@RequestMapping("formas-pago")
public class FormaPagoController {
   
   private final FormaPagoService service;

   public FormaPagoController(FormaPagoService service) {
      this.service = service;
   }

   @ApiOperation(value = "Obtener catálogo de Formas de Pago", httpMethod = "GET", response = Collection.class)
   @ApiResponses(value = {
   	@ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa", response = FormaPagoDTO.class),
	@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Error en parametros", response = ApiError.class),
	@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar petición", response = ApiError.class),
	@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida", response = ApiError.class),
	@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acceso denegado", response = ApiError.class)
   })
   @GetMapping
   @PreAuthorize("isAuthenticaded()")
   public ResponseEntity<Collection<FormaPagoDTO>> findAll() throws CatalogException {
      return new ResponseEntity<Collection<FormaPagoDTO>> (
		      service.findAll(),
		      HttpStatus.OK
		      );
   }
}
