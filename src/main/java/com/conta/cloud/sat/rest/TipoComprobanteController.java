package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.TipoComprobanteDTO;
import com.conta.cloud.sat.service.TipoComprobanteService;
import com.conta.cloud.sat.exception.CatalogException;

import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController()
@RequestMapping("tipos/comprobantes")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_TIPO_COMPROBANTE}) 
public class TipoComprobanteController {
    
    private TipoComprobanteService tipoComprobanteService;

    public TipoComprobanteController(TipoComprobanteService tipoComprobanteService) {
       this.tipoComprobanteService = tipoComprobanteService;
    }

    @ApiOperation(value = "Crear Tipo de Comprobante", httpMethod = "POST", response = TipoComprobanteDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = TipoComprobanteDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TipoComprobanteDTO> addTipoComprobante(
        @RequestBody @Validated TipoComprobanteDTO tipoComprobanteDTO
    ) throws CatalogException{
        TipoComprobanteDTO dto = this.tipoComprobanteService.addTipoComprobante(tipoComprobanteDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualizar Tipo de Comprobante",httpMethod = "PUT", response = TipoComprobanteDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoComprobanteDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TipoComprobanteDTO> updateTipoComprobante(
        @RequestBody @Validated TipoComprobanteDTO tipoComprobanteDTO,
        @PathVariable String id
        ) throws CatalogException {    
        tipoComprobanteDTO.setId(id);
        TipoComprobanteDTO dto = this.tipoComprobanteService.updateTipoComprobante(tipoComprobanteDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener Tipo de Comprobante usando Id", httpMethod = "GET", response = TipoComprobanteDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoComprobanteDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TipoComprobanteDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.tipoComprobanteService.findById(id), HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener lista de Tipo Comprobante", httpMethod = "GET", response = Collection.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoComprobanteDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<TipoComprobanteDTO>> findAll() throws CatalogException{        
        Collection<TipoComprobanteDTO> dtoList = this.tipoComprobanteService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @ApiOperation(value = "", httpMethod = "GET", response = Page.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoComprobanteDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<TipoComprobanteDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<TipoComprobanteDTO> paginatedResult = this.tipoComprobanteService.getPaginatedResult(page, size, columnToOrder, orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
