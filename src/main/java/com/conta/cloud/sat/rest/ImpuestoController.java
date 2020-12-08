package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.dto.ImpuestoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.service.ImpuestoService;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.config.SwaggerConfig;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;

@RestController
@RequestMapping("impuestos")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = SwaggerConfig.TAG_IMPUESTOS)
public class ImpuestoController {

   private final ImpuestoService service;

   public ImpuestoController(ImpuestoService service) {
      this.service = service;
   }

   @ApiOperation(value = "Obtener catálogo de Impuestos",httpMethod = "GET", response = Collection.class)
   @ApiResponses(value = {
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa", response = ImpuestoDTO.class),
      @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar petición", response = ApiError.class),
      @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acceso denegado", response = ApiError.class),
      @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida", response = ApiError.class)
   })
   @GetMapping
   @PreAuthorize(value = "isAuthenticated()")
   public ResponseEntity<Collection<ImpuestoDTO>> findAll() throws CatalogException {
      return new ResponseEntity<Collection<ImpuestoDTO>>(
		      service.findAll(),
		      HttpStatus.OK
		      );
   }
}
