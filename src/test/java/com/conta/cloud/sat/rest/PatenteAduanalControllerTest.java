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

import com.conta.cloud.sat.dto.PatenteAduanalDTO;
import com.conta.cloud.sat.service.PatenteAduanalService;

@ExtendWith(MockitoExtension.class)
@DisplayName("PatenteAduanal REST Testing")
public class PatenteAduanalControllerTest {

    @Mock
    private PatenteAduanalService service;

    @InjectMocks
    private PatenteAduanalController controller;

    private PatenteAduanalDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private PatenteAduanalDTO mockDTO() {
        PatenteAduanalDTO dto = new PatenteAduanalDTO();
        dto.setId(expectedId);
        dto.setFechaFin("01/01/2021");
        dto.setFechaInicio("01/01/2019");
        return dto;
    }

    @Test
    public void addPatenteAduanal_successTransaction_responseCreated() throws Exception {
        PatenteAduanalDTO resultFromService = mockDTO();
        
        when(service.addPatenteAduanal(any(PatenteAduanalDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<PatenteAduanalDTO> responseResult = (ResponseEntity)controller.addPatenteAduanal(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        PatenteAduanalDTO resultDTO = responseResult.getBody();
        assertEquals(expectedId, resultDTO.getId());
        assertEquals("01/01/2019", resultDTO.getFechaInicio());
        assertEquals("01/01/2021", resultDTO.getFechaFin());
    }

     @Test
    public void updatePatenteAduanal_sucessTransaction_responseOK() throws Exception {
        PatenteAduanalDTO resultFromService = mockDTO();

        when(service.updatePatenteAduanal(any(PatenteAduanalDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<PatenteAduanalDTO> responseResult = (ResponseEntity)controller.updatePatenteAduanal(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        PatenteAduanalDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        PatenteAduanalDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<PatenteAduanalDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        PatenteAduanalDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        PatenteAduanalDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<PatenteAduanalDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<PatenteAduanalDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<PatenteAduanalDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<PatenteAduanalDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
    }


}
