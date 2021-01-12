package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.PatenteAduanalDTO;
import com.conta.cloud.sat.service.PatenteAduanalService;
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
@RequestMapping("patentes-aduanales")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_PATENTE_ADUANAL})
public class PatenteAduanalController {
    
    private PatenteAduanalService patenteAduanalService;

    public PatenteAduanalController(PatenteAduanalService patenteAduanalService) {
       this.patenteAduanalService = patenteAduanalService;
    }

    @ApiOperation(value = "Crear Patente Aduanal", httpMethod = "POST", response = PatenteAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = PatenteAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<PatenteAduanalDTO> addPatenteAduanal(
        @RequestBody @Validated PatenteAduanalDTO patenteAduanalDTO
    ) throws CatalogException{
        PatenteAduanalDTO dto = this.patenteAduanalService.addPatenteAduanal(patenteAduanalDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    
    @ApiOperation(value = "Actualizar Patente Aduanal", httpMethod = "PUT", response = PatenteAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = PatenteAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<PatenteAduanalDTO> updatePatenteAduanal(
        @RequestBody @Validated PatenteAduanalDTO patenteAduanalDTO,
        @PathVariable String id
        ) throws CatalogException {    
        patenteAduanalDTO.setId(id);
        PatenteAduanalDTO dto = this.patenteAduanalService.updatePatenteAduanal(patenteAduanalDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener Patente Aduanal", httpMethod = "GET", response = PatenteAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = PatenteAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })    
    @GetMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<PatenteAduanalDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.patenteAduanalService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener lista de Patentes", httpMethod = "GET", response = PatenteAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = PatenteAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<PatenteAduanalDTO>> findAll() throws CatalogException{        
        Collection<PatenteAduanalDTO> dtoList = this.patenteAduanalService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener pagina de Patentes", httpMethod = "GET", response = PatenteAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = PatenteAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")    
    public HttpEntity<Page<PatenteAduanalDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<PatenteAduanalDTO> paginatedResult = this.patenteAduanalService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
