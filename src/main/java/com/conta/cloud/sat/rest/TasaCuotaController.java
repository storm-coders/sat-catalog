package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.TasaCuotaDTO;
import com.conta.cloud.sat.service.TasaCuotaService;
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
@RequestMapping("tasas-cuotas")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_TASA_CUOTA}) 
public class TasaCuotaController {
    
    private TasaCuotaService tasaCuotaService;

    public TasaCuotaController(TasaCuotaService tasaCuotaService) {
       this.tasaCuotaService = tasaCuotaService;
    }

    @ApiOperation(value = "Crear Tasa/Cuota", httpMethod = "POST", response = TasaCuotaDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = TasaCuotaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TasaCuotaDTO> addTasaCuota(
        @RequestBody @Validated TasaCuotaDTO tasaCuotaDTO
    ) throws CatalogException{
        TasaCuotaDTO dto = this.tasaCuotaService.addTasaCuota(tasaCuotaDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualizar Tasa/Cuota", httpMethod = "PUT", response = TasaCuotaDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TasaCuotaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TasaCuotaDTO> updateTasaCuota(
        @RequestBody @Validated TasaCuotaDTO tasaCuotaDTO,
        @PathVariable String id
        ) throws CatalogException {    
        tasaCuotaDTO.setId(id);
        TasaCuotaDTO dto = this.tasaCuotaService.updateTasaCuota(tasaCuotaDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener detalles de Tasa/Cuota", httpMethod = "GET", response = TasaCuotaDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TasaCuotaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="{id}")    
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TasaCuotaDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.tasaCuotaService.findById(id), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Obtener lista de Tasa/Cuota", httpMethod = "GET", response = Collection.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TasaCuotaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<TasaCuotaDTO>> findAll() throws CatalogException{        
        Collection<TasaCuotaDTO> dtoList = this.tasaCuotaService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener pagina de Tasa/Cuota", httpMethod = "GET", response = Page.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TasaCuotaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<TasaCuotaDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<TasaCuotaDTO> paginatedResult = this.tasaCuotaService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
