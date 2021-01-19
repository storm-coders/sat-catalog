package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.UsoCFDIDTO;
import com.conta.cloud.sat.service.UsoCFDIService;
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
@RequestMapping("usos/cfdi")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = {SwaggerConfig.TAG_USO_CFDI})
public class UsoCFDIController {
    
    private UsoCFDIService usoCFDIService;

    public UsoCFDIController(UsoCFDIService usoCFDIService) {
       this.usoCFDIService = usoCFDIService;
    }

    @ApiOperation(value = "Crear nuevo Uso CFDI", httpMethod = "POST", response = UsoCFDIDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created", response = UsoCFDIDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<UsoCFDIDTO> addUsoCFDI(
        @RequestBody @Validated UsoCFDIDTO usoCFDIDTO
    ) throws CatalogException{
        UsoCFDIDTO dto = this.usoCFDIService.addUsoCFDI(usoCFDIDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualizar uso de CFDI", httpMethod = "PUT", response = UsoCFDIDTO.class)    
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = UsoCFDIDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<UsoCFDIDTO> updateUsoCFDI(
        @RequestBody @Validated UsoCFDIDTO usoCFDIDTO,
        @PathVariable String id
        ) throws CatalogException {    
        usoCFDIDTO.setId(id);
        UsoCFDIDTO dto = this.usoCFDIService.updateUsoCFDI(usoCFDIDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener uso CFDI usando ID", httpMethod = "GET", response = UsoCFDIDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = UsoCFDIDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="{id}")    
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<UsoCFDIDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.usoCFDIService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener lista de Uso CFDI", httpMethod = "GET", response = Collection.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = UsoCFDIDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<UsoCFDIDTO>> findAll() throws CatalogException{        
        Collection<UsoCFDIDTO> dtoList = this.usoCFDIService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener pagina de Uso CFDI", httpMethod = "GET", response = Page.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success", response = UsoCFDIDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error"),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = "Acces denied"),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = "Not authorized")
    })
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<UsoCFDIDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<UsoCFDIDTO> paginatedResult = this.usoCFDIService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
