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

import com.conta.cloud.sat.dto.TipoRelacionDTO;
import com.conta.cloud.sat.service.TipoRelacionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TipoRelacion REST Testing")
public class TipoRelacionControllerTest {

    @Mock
    private TipoRelacionService service;

    @InjectMocks
    private TipoRelacionController controller;

    private TipoRelacionDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private TipoRelacionDTO mockDTO() {
        TipoRelacionDTO dto = new TipoRelacionDTO();
        dto.setId(expectedId);
        dto.setDescripcion("Nota Credito");
        dto.setFechaInicio("01/01/2019");
        dto.setFechaFin("01/01/2021");
        return dto;
    }

    private void assertDTOProperties(TipoRelacionDTO dto) {
        assertEquals(expectedId, dto.getId());
        assertEquals(expectedDTO.getDescripcion(), dto.getDescripcion());
        assertEquals(expectedDTO.getFechaInicio(), dto.getFechaInicio());
        assertEquals(expectedDTO.getFechaFin(), dto.getFechaFin());
    }

    @Test
    public void addTipoRelacion_successTransaction_responseCreated() throws Exception {
        TipoRelacionDTO resultFromService = mockDTO();
        
        when(service.addTipoRelacion(any(TipoRelacionDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<TipoRelacionDTO> responseResult = (ResponseEntity)controller.addTipoRelacion(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TipoRelacionDTO resultDTO = responseResult.getBody();
        assertDTOProperties(resultDTO);
    }

     @Test
    public void updateTipoRelacion_sucessTransaction_responseOK() throws Exception {
        TipoRelacionDTO resultFromService = mockDTO();

        when(service.updateTipoRelacion(any(TipoRelacionDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<TipoRelacionDTO> responseResult = (ResponseEntity)controller.updateTipoRelacion(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TipoRelacionDTO resultDTO = responseResult.getBody();
        assertDTOProperties(resultDTO);
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        TipoRelacionDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<TipoRelacionDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TipoRelacionDTO resultDTO = responseResult.getBody();
        assertDTOProperties(resultDTO);
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        TipoRelacionDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<TipoRelacionDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<TipoRelacionDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<TipoRelacionDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<TipoRelacionDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
        assertDTOProperties(paginatedResult.getContent().get(0));
    }


}
