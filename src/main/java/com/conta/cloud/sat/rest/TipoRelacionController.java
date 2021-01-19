package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.TipoRelacionDTO;
import com.conta.cloud.sat.service.TipoRelacionService;
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
@RequestMapping("tipos/relaciones")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_TIPO_RELACION})
public class TipoRelacionController {
    
    private TipoRelacionService tipoRelacionService;

    public TipoRelacionController(TipoRelacionService tipoRelacionService) {
       this.tipoRelacionService = tipoRelacionService;
    }

    @ApiOperation(value = "Crear nuevo TipoRelacion", httpMethod = "POST", response = TipoRelacionDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = TipoRelacionDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TipoRelacionDTO> addTipoRelacion(
        @RequestBody @Validated TipoRelacionDTO tipoRelacionDTO
    ) throws CatalogException{
        TipoRelacionDTO dto = this.tipoRelacionService.addTipoRelacion(tipoRelacionDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualizar TipoRelacion", httpMethod = "PUT", response = TipoRelacionDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoRelacionDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TipoRelacionDTO> updateTipoRelacion(
        @RequestBody @Validated TipoRelacionDTO tipoRelacionDTO,
        @PathVariable String id
        ) throws CatalogException {    
        tipoRelacionDTO.setId(id);
        TipoRelacionDTO dto = this.tipoRelacionService.updateTipoRelacion(tipoRelacionDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener TipoRelacion", httpMethod = "GET", response = TipoRelacionDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoRelacionDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<TipoRelacionDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.tipoRelacionService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener lista de TipoRelacion", httpMethod = "GET", response = TipoRelacionDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoRelacionDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<TipoRelacionDTO>> findAll() throws CatalogException{        
        Collection<TipoRelacionDTO> dtoList = this.tipoRelacionService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener pagina de TipoRelacion", httpMethod = "GET", response = TipoRelacionDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = TipoRelacionDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<TipoRelacionDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<TipoRelacionDTO> paginatedResult = this.tipoRelacionService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
