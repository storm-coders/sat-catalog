package com.conta.cloud.sat.rest;

import com.conta.cloud.sat.config.SwaggerConfig;
import com.conta.cloud.sat.dto.MonedaDTO;
import com.conta.cloud.sat.service.MonedaService;
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
@RequestMapping("monedas")
@Api(produces = SwaggerConfig.APPLICATION_JSON, tags = SwaggerConfig.TAG_MONEDA)
public class MonedaController {
    
    private MonedaService monedaService;

    public MonedaController(MonedaService monedaService) {
       this.monedaService = monedaService;
    }

    
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = WebAppConstants.CREATED_MESSAGE, response = MonedaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE)
    })
    @ApiOperation(value = "Crear nueva Moneda", httpMethod = "POST", response = MonedaDTO.class)
    @PostMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<MonedaDTO> addMoneda(
        @RequestBody @Validated MonedaDTO monedaDTO
    ) throws CatalogException{
        MonedaDTO dto = this.monedaService.addMoneda(monedaDTO);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }


    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = WebAppConstants.SUCCESS_MESSAGE, response = MonedaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE),
	@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = WebAppConstants.NOT_FOUND_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE)
    })
    @ApiOperation(value = "Actualizar Moneda", httpMethod = "PUT", response = MonedaDTO.class)
    @PutMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<MonedaDTO> updateMoneda(
        @RequestBody @Validated MonedaDTO monedaDTO,
        @PathVariable String id
        ) throws CatalogException {    
        monedaDTO.setId(id);
        MonedaDTO dto = this.monedaService.updateMoneda(monedaDTO);        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @ApiResponses(value = {
	@ApiResponse(code = HttpServletResponse.SC_OK, message = WebAppConstants.SUCCESS_MESSAGE, response = MonedaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE),
	@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = WebAppConstants.NOT_FOUND_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE)
    })
    @ApiOperation(value = "Obtener Moneda", httpMethod = "GET", response = MonedaDTO.class)
    @GetMapping(value="{id}")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<MonedaDTO> findById(@PathVariable String id) throws CatalogException{        
        return new ResponseEntity<>(this.monedaService.findById(id), HttpStatus.OK);
    }


    @ApiResponses(value = {
	@ApiResponse(code = HttpServletResponse.SC_OK, message = WebAppConstants.SUCCESS_MESSAGE, response = MonedaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE)
    })
    @ApiOperation(value = "Obtener lista de Monedas", httpMethod = "GET", response = MonedaDTO.class)
    @GetMapping()
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Collection<MonedaDTO>> findAll() throws CatalogException{        
        Collection<MonedaDTO> dtoList = this.monedaService.findAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @ApiResponses(value = {
	@ApiResponse(code = HttpServletResponse.SC_OK, message = WebAppConstants.SUCCESS_MESSAGE, response = MonedaDTO.class),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = WebAppConstants.BAD_REQUEST_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = WebAppConstants.INTERNAL_SERVER_ERROR_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = WebAppConstants.FORBIDDEN_MESSAGE),
        @ApiResponse(code = HttpServletResponse.SC_UNAUTHORIZED, message = WebAppConstants.NOT_AUTHORIZED_MESSAGE)
    })
    @ApiOperation(value = "Obtener pagina de Monedas", httpMethod = "GET", response = MonedaDTO.class)
    @GetMapping(value="paginatedResult")
    @PreAuthorize(value = "isAuthenticated()")
    public HttpEntity<Page<MonedaDTO>> getPaginatedResult(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "20") Integer size,
            @RequestParam(required = false) String columnToOrder,
            @RequestParam(required = false) String orderType
        ) throws CatalogException {
        Page<MonedaDTO> paginatedResult = this.monedaService.getPaginatedResult(page,size,columnToOrder,orderType);
        return new ResponseEntity<>(paginatedResult, HttpStatus.OK);
    }
}
