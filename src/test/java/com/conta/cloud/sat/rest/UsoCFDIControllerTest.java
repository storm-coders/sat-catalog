package com.conta.cloud.sat.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.conta.cloud.sat.dto.UsoCFDIDTO;
import com.conta.cloud.sat.service.UsoCFDIService;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsoCFDI REST Testing")
public class UsoCFDIControllerTest {

    @Mock
    private UsoCFDIService service;

    @InjectMocks
    private UsoCFDIController controller;

    private UsoCFDIDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private UsoCFDIDTO mockDTO() {
        UsoCFDIDTO dto = new UsoCFDIDTO();
        dto.setId(expectedId);
        dto.setDescripcion("Gastos en general");
        dto.setPersonaFisica(Boolean.TRUE);
        dto.setPersonaMoral(Boolean.TRUE);
        dto.setFechaInicio("01/01/2017");
        return dto;
    }

    private void assertDTOProperties(UsoCFDIDTO dto) {
        assertEquals(expectedId, dto.getId());
        assertEquals(expectedDTO.getDescripcion(), dto.getDescripcion());
        assertEquals(expectedDTO.getFechaInicio(), dto.getFechaInicio());
        assertNull(dto.getFechaFin());
        assertTrue(dto.getPersonaFisica());
        assertTrue(dto.getPersonaMoral());
    }

    @Test
    public void addUsoCFDI_successTransaction_responseCreated() throws Exception {
        UsoCFDIDTO resultFromService = mockDTO();
        
        when(service.addUsoCFDI(any(UsoCFDIDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<UsoCFDIDTO> responseResult = (ResponseEntity)controller.addUsoCFDI(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        UsoCFDIDTO resultDTO = responseResult.getBody();
        assertDTOProperties(resultDTO);
    }

     @Test
    public void updateUsoCFDI_sucessTransaction_responseOK() throws Exception {
        UsoCFDIDTO resultFromService = mockDTO();

        when(service.updateUsoCFDI(any(UsoCFDIDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<UsoCFDIDTO> responseResult = (ResponseEntity)controller.updateUsoCFDI(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        UsoCFDIDTO resultDTO = responseResult.getBody();
        assertDTOProperties(resultDTO);
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        UsoCFDIDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<UsoCFDIDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        UsoCFDIDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
        assertDTOProperties(resultDTO);
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        UsoCFDIDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<UsoCFDIDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<UsoCFDIDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
        // one element match
        assertDTOProperties(collectionResult.iterator().next());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<UsoCFDIDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<UsoCFDIDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
        assertDTOProperties(paginatedResult.getContent().get(0));
    }


}
