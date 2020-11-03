package com.conta.cloud.sat.rest;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.PaisDTO;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.service.PaisService;

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

@RestController
@RequestMapping("direcciones/paises")
@Api(produces = "application/json", tags = SwaggerConfig.PAIS_TAG)
public class PaisController {

    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @ApiOperation(value = "Obtener catálogo de Paises",httpMethod = "GET", response = Collection.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpServletResponse.SC_OK, message = "Petición exitosa"),
		@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al procesar peticion", response = ApiError.class),
		@ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Accesso denegado"),
		@ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Autorización requerida"),
	})
    @GetMapping
    @PreAuthorize(value = "isAuthenticated()")
    public ResponseEntity<Collection<PaisDTO>> findPaises() throws CatalogException {
        return new ResponseEntity<Collection<PaisDTO>>(
            paisService.findPaises(),
            HttpStatus.OK
        );
    }
}