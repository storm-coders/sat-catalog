package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.RegimenFiscalDTO;
import com.conta.cloud.sat.service.RegimenFiscalService;
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
@RequestMapping("regimenes-fiscales")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_REGIMEN_FISCAL})
public class RegimenFiscalController {
    
    private RegimenFiscalService regimenFiscalService;

    public RegimenFiscalController(RegimenFiscalService regimenFiscalService) {
       this.regimenFiscalService = regimenFiscalService;
    }

    
    @ApiOperation(value = "Crear nuevo Regimen Fiscal", httpMethod = "POST", response = RegimenFiscalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = RegimenFiscalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<RegimenFiscalDTO> addRegimenFiscal(
        @RequestBody @Validated RegimenFiscalDTO regimenFiscalDTO
    ) throws CatalogException{
        RegimenFiscalDTO dto = this.regimenFiscalService.addRegimenFiscal(regimenFiscalDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    
    @ApiOperation(value = "Actualizar Regimen Fiscal", httpMethod = "PUT", response = RegimenFiscalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = RegimenFiscalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<RegimenFiscalDTO> updateRegimenFiscal(
        @RequestBody @Validated RegimenFiscalDTO regimenFiscalDTO,
        @PathVariable String id
        ) throws CatalogException {    
        regimenFiscalDTO.setId(id);
        RegimenFiscalDTO dto = this.regimenFiscalService.updateRegimenFiscal(regimenFiscalDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener Regimen Fiscal", httpMethod = "GET", response = RegimenFiscalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = RegimenFiscalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="{id}")    
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<RegimenFiscalDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.regimenFiscalService.findById(id), HttpStatus.OK);
    }

    
    @ApiOperation(value = "Obtener lista de Regimenes Fiscales")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = RegimenFiscalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<RegimenFiscalDTO>> findAll() throws CatalogException{        
        Collection<RegimenFiscalDTO> dtoList = this.regimenFiscalService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener pagina de Regiemenes Fiscales", httpMethod = "GET", response = RegimenFiscalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = RegimenFiscalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<RegimenFiscalDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<RegimenFiscalDTO> paginatedResult = this.regimenFiscalService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
