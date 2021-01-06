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

import com.conta.cloud.sat.dto.NumeroPedimentoAduanalDTO;
import com.conta.cloud.sat.service.NumeroPedimentoAduanalService;

@ExtendWith(MockitoExtension.class)
@DisplayName("NumeroPedimentoAduanal REST Testing")
public class NumeroPedimentoAduanalControllerTest {

    @Mock
    private NumeroPedimentoAduanalService service;

    @InjectMocks
    private NumeroPedimentoAduanalController controller;

    private NumeroPedimentoAduanalDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private NumeroPedimentoAduanalDTO mockDTO() {
        NumeroPedimentoAduanalDTO dto = new NumeroPedimentoAduanalDTO();
        dto.setId(expectedId);
        dto.setCantidad(100);
        dto.setEjercicio(2020);
        dto.setFechaFin("01/01/2021");
        dto.setFechaInicio("01/01/2019");
        dto.setIdAduana("01");
        dto.setPatente("2001");
        return dto;
    }

    @Test
    public void addNumeroPedimentoAduanal_successTransaction_responseCreated() throws Exception {
        NumeroPedimentoAduanalDTO resultFromService = mockDTO();
        
        when(service.addNumeroPedimentoAduanal(any(NumeroPedimentoAduanalDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<NumeroPedimentoAduanalDTO> responseResult = (ResponseEntity)controller.addNumeroPedimentoAduanal(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        NumeroPedimentoAduanalDTO resultDTO = responseResult.getBody();
        assertEquals(expectedId, resultDTO.getId());
    }

     @Test
    public void updateNumeroPedimentoAduanal_sucessTransaction_responseOK() throws Exception {
        NumeroPedimentoAduanalDTO resultFromService = mockDTO();

        when(service.updateNumeroPedimentoAduanal(any(NumeroPedimentoAduanalDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<NumeroPedimentoAduanalDTO> responseResult = (ResponseEntity)controller.updateNumeroPedimentoAduanal(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        NumeroPedimentoAduanalDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        NumeroPedimentoAduanalDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<NumeroPedimentoAduanalDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        NumeroPedimentoAduanalDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        NumeroPedimentoAduanalDTO resultFromService = mockDTO();
        when(service.findAll(eq("")))
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<NumeroPedimentoAduanalDTO>>   responseResult = (ResponseEntity)controller.findAll("");

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<NumeroPedimentoAduanalDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<NumeroPedimentoAduanalDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<NumeroPedimentoAduanalDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
        
    }


}
