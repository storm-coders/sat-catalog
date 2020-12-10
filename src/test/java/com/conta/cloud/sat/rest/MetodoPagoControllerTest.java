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

import com.conta.cloud.sat.dto.MetodoPagoDTO;
import com.conta.cloud.sat.service.MetodoPagoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MetodoPago REST Testing")
public class MetodoPagoControllerTest {

    @Mock
    private MetodoPagoService service;

    @InjectMocks
    private MetodoPagoController controller;

    private MetodoPagoDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private MetodoPagoDTO mockDTO() {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setId(expectedId);
        dto.setDescripcion("Pago 01");
        dto.setFechaFin("01/01/2021");
        dto.setFechaInicio("01/01/2019");
        return dto;
    }

    @Test
    public void addMetodoPago_successTransaction_responseCreated() throws Exception {
        MetodoPagoDTO resultFromService = mockDTO();
        
        when(service.addMetodoPago(any(MetodoPagoDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<MetodoPagoDTO> responseResult = (ResponseEntity)controller.addMetodoPago(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        MetodoPagoDTO resultDTO = responseResult.getBody();
        assertEquals(expectedId, resultDTO.getId());
        assertEquals(expectedDTO.getDescripcion(), resultDTO.getDescripcion());
        assertEquals(expectedDTO.getFechaFin(), resultDTO.getFechaFin());
        assertEquals(expectedDTO.getFechaInicio(), resultDTO.getFechaInicio());
    }

     @Test
    public void updateMetodoPago_sucessTransaction_responseOK() throws Exception {
        MetodoPagoDTO resultFromService = mockDTO();

        when(service.updateMetodoPago(any(MetodoPagoDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<MetodoPagoDTO> responseResult = (ResponseEntity)controller.updateMetodoPago(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        MetodoPagoDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
        assertEquals(expectedDTO.getDescripcion(), resultDTO.getDescripcion());
        assertEquals(expectedDTO.getFechaFin(), resultDTO.getFechaFin());
        assertEquals(expectedDTO.getFechaInicio(), resultDTO.getFechaInicio());
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        MetodoPagoDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<MetodoPagoDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        MetodoPagoDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
        assertEquals(expectedDTO.getDescripcion(), resultDTO.getDescripcion());
        assertEquals(expectedDTO.getFechaFin(), resultDTO.getFechaFin());
        assertEquals(expectedDTO.getFechaInicio(), resultDTO.getFechaInicio());
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        MetodoPagoDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<MetodoPagoDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<MetodoPagoDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<MetodoPagoDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<MetodoPagoDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
    }


}
