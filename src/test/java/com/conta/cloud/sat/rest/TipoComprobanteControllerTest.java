package com.conta.cloud.sat.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.conta.cloud.sat.dto.TipoComprobanteDTO;
import com.conta.cloud.sat.service.TipoComprobanteService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TipoComprobante REST Testing")
public class TipoComprobanteControllerTest {

    @Mock
    private TipoComprobanteService service;

    @InjectMocks
    private TipoComprobanteController controller;

    private TipoComprobanteDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private TipoComprobanteDTO mockDTO() {
        TipoComprobanteDTO dto = new TipoComprobanteDTO();
        dto.setId(expectedId);
        dto.setDescripcion("Ingreso");
        dto.setFechaFin("01/01/2021");
        dto.setFechaInicio("01/01/2019");
        dto.setValorMaximo(new BigDecimal("999999999999999999.9999990000"));
        return dto;
    }

    private void assertDTOProps(TipoComprobanteDTO dto) {
        assertEquals(expectedId, dto.getId());
        assertEquals("Ingreso", dto.getDescripcion());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals(new BigDecimal("999999999999999999.9999990000"), dto.getValorMaximo());
    }

    @Test
    public void addTipoComprobante_successTransaction_responseCreated() throws Exception {
        TipoComprobanteDTO resultFromService = mockDTO();
        
        when(service.addTipoComprobante(any(TipoComprobanteDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<TipoComprobanteDTO> responseResult = (ResponseEntity)controller.addTipoComprobante(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TipoComprobanteDTO resultDTO = responseResult.getBody();
        assertDTOProps(resultDTO);
    }

     @Test
    public void updateTipoComprobante_sucessTransaction_responseOK() throws Exception {
        TipoComprobanteDTO resultFromService = mockDTO();

        when(service.updateTipoComprobante(any(TipoComprobanteDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<TipoComprobanteDTO> responseResult = (ResponseEntity)controller.updateTipoComprobante(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TipoComprobanteDTO resultDTO = responseResult.getBody();
        assertDTOProps(resultDTO);
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        TipoComprobanteDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<TipoComprobanteDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TipoComprobanteDTO resultDTO = responseResult.getBody();
        assertDTOProps(resultDTO);      
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        TipoComprobanteDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<TipoComprobanteDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<TipoComprobanteDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<TipoComprobanteDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<TipoComprobanteDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
        assertDTOProps(paginatedResult.getContent().get(0));
    }


}
