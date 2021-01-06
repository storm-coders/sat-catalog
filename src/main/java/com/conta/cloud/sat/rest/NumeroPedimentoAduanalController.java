package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.NumeroPedimentoAduanalDTO;
import com.conta.cloud.sat.service.NumeroPedimentoAduanalService;
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
@RequestMapping("numeros-pedimento-aduanal")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_NO_PEDIMENTO_ADUANAL})
public class NumeroPedimentoAduanalController {
    
    private NumeroPedimentoAduanalService numeroPedimentoAduanalService;

    public NumeroPedimentoAduanalController(NumeroPedimentoAduanalService numeroPedimentoAduanalService) {
       this.numeroPedimentoAduanalService = numeroPedimentoAduanalService;
    }

    
    @ApiOperation(httpMethod = "POST", value = "Crear No. Pedimento Aduanal", response = NumeroPedimentoAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = NumeroPedimentoAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<NumeroPedimentoAduanalDTO> addNumeroPedimentoAduanal(
        @RequestBody @Validated NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO
    ) throws CatalogException{
        NumeroPedimentoAduanalDTO dto = this.numeroPedimentoAduanalService.addNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualizar No. Pedimento Aduanal", httpMethod = "PUT", response = NumeroPedimentoAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = NumeroPedimentoAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<NumeroPedimentoAduanalDTO> updateNumeroPedimentoAduanal(
        @RequestBody @Validated NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO,
        @PathVariable String id
        ) throws CatalogException {    
        numeroPedimentoAduanalDTO.setId(id);
        NumeroPedimentoAduanalDTO dto = this.numeroPedimentoAduanalService.updateNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener detallede No. Pedimento", httpMethod = "GET", response = NumeroPedimentoAduanalDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = NumeroPedimentoAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })    
    @GetMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<NumeroPedimentoAduanalDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.numeroPedimentoAduanalService.findById(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Obtener lista de No. de pedimento", httpMethod = "GET", response = Collection.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = NumeroPedimentoAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<NumeroPedimentoAduanalDTO>> findAll(
        @RequestParam(required = false, defaultValue = "")String idAduana) throws CatalogException{        
        Collection<NumeroPedimentoAduanalDTO> dtoList = this.numeroPedimentoAduanalService.findAll(idAduana);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener pagina de No. de Pedimento", httpMethod = "GET", response = Page.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = NumeroPedimentoAduanalDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<NumeroPedimentoAduanalDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<NumeroPedimentoAduanalDTO> paginatedResult = this.numeroPedimentoAduanalService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
