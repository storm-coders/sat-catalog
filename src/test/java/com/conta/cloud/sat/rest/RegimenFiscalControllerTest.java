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

import com.conta.cloud.sat.dto.RegimenFiscalDTO;
import com.conta.cloud.sat.service.RegimenFiscalService;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegimenFiscal REST Testing")
public class RegimenFiscalControllerTest {

    @Mock
    private RegimenFiscalService service;

    @InjectMocks
    private RegimenFiscalController controller;

    private RegimenFiscalDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private RegimenFiscalDTO mockDTO() {
        RegimenFiscalDTO dto = new RegimenFiscalDTO();
        dto.setId(expectedId);
        dto.setDescripcion("Test description");
        dto.setFechaFin("01/01/2021");
        dto.setFechaInicio("01/01/2019");
        dto.setPersonaFisica(Boolean.TRUE);
        dto.setPersonaMoral(Boolean.FALSE);
        return dto;
    }

    @Test
    public void addRegimenFiscal_successTransaction_responseCreated() throws Exception {
        RegimenFiscalDTO resultFromService = mockDTO();
        
        when(service.addRegimenFiscal(any(RegimenFiscalDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<RegimenFiscalDTO> responseResult = (ResponseEntity)controller.addRegimenFiscal(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        RegimenFiscalDTO resultDTO = responseResult.getBody();
        assertEquals(expectedId, resultDTO.getId());
    }

     @Test
    public void updateRegimenFiscal_sucessTransaction_responseOK() throws Exception {
        RegimenFiscalDTO resultFromService = mockDTO();

        when(service.updateRegimenFiscal(any(RegimenFiscalDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<RegimenFiscalDTO> responseResult = (ResponseEntity)controller.updateRegimenFiscal(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        RegimenFiscalDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        RegimenFiscalDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<RegimenFiscalDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        RegimenFiscalDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        RegimenFiscalDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<RegimenFiscalDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<RegimenFiscalDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());

    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<RegimenFiscalDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<RegimenFiscalDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());        
    }

}
