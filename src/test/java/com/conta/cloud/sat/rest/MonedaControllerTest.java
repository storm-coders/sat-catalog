package com.conta.cloud.sat.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.MonedaDTO;
import com.conta.cloud.sat.service.MonedaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Moneda REST Testing")
public class MonedaControllerTest {

    @Mock
    private MonedaService service;

    @InjectMocks
    private MonedaController controller;

    private MonedaDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private MonedaDTO mockDTO() {
        MonedaDTO dto = new MonedaDTO();
        dto.setId(expectedId);
        dto.setDescripcion("Pesos");
        dto.setNoDecimales(2);
        dto.setPorcentajeVariacion(16);
        dto.setFechaInicio("01/12/2020");
        dto.setFechaFin("01/12/2021");
        return dto;
    }

    @Test
    public void addMoneda_sucessTransaction_responseOK() throws Exception {
        MonedaDTO resultFromService = mockDTO();
        
        when(service.addMoneda(any(MonedaDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<MonedaDTO> responseResult = (ResponseEntity)controller.addMoneda(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        MonedaDTO resultDTO = responseResult.getBody();
        assertEquals(expectedId, resultDTO.getId());
        assertEquals("Pesos", resultDTO.getDescripcion());
        assertEquals(2, resultDTO.getNoDecimales());
        assertEquals(16, resultDTO.getPorcentajeVariacion());
        assertEquals("01/12/2020", resultDTO.getFechaInicio());
        assertEquals("01/12/2021", resultDTO.getFechaFin());
    }

    @Test
    public void updateMoneda_sucessTransaction_responseOK() throws Exception {
        MonedaDTO resultFromService = mockDTO();

        when(service.updateMoneda(any(MonedaDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<MonedaDTO> responseResult = (ResponseEntity)controller.updateMoneda(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        MonedaDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }


    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        MonedaDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<MonedaDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        MonedaDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
        
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        MonedaDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<MonedaDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<MonedaDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }


    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<MonedaDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<MonedaDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
    }
}