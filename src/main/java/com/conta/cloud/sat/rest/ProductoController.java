package com.conta.cloud.sat.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.ProductoDTO;
import com.conta.cloud.sat.rest.exception.ApiError;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.service.ProductoService;


import java.util.Collection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = SwaggerConfig.TAG_PRODUCTO)
@RestController
@RequestMapping("productos")
public class ProductoController {

   private final ProductoService productoService;

   public ProductoController(ProductoService productoService) {
      this.productoService = productoService;
   }

   @ApiOperation(value = "Obtener catálogo de productos", httpMethod = "GET", response = Collection.class)
   @ApiResponses(value = {
      @ApiResponse(code = HttpServletResponse.SC_OK, message = WebAppConstants.SUCCESS_MESSAGE, response = ProductoDTO.class),
      @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE, response = ApiError.class),
      @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message= WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE, response = ApiError.class),
      @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE, response = ApiError.class),
      @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE, response = ApiError.class)
   })
   @GetMapping()
   @PreAuthorize("isAuthenticaded()")
   public ResponseEntity<Collection<ProductoDTO>> findProductos(
		   @ApiParam(name="id", value="id de Producto a buscar") //
		   @RequestParam(required = false)//
		   String id,//
		   @ApiParam(name="desccripcion", value= "descripcion de Producto a buscar")//
		   @RequestParam(required = false)//
		   String descripcion) throws CatalogException {
      return new ResponseEntity<Collection<ProductoDTO>>(
		      productoService.findProductos(id, descripcion),
		      HttpStatus.OK
		      );
   }

}

