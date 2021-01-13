package com.conta.cloud.sat.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.conta.cloud.sat.dto.TasaCuotaDTO;
import com.conta.cloud.sat.service.TasaCuotaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TasaCuota REST Testing")
public class TasaCuotaControllerTest {

    @Mock
    private TasaCuotaService service;

    @InjectMocks
    private TasaCuotaController controller;

    private TasaCuotaDTO expectedDTO;

    private String expectedId = "test-id";

    @BeforeEach
    public void setup() {
        expectedDTO = mockDTO();
    }

    private TasaCuotaDTO mockDTO() {
        TasaCuotaDTO dto = new TasaCuotaDTO();
        dto.setId(expectedId);
        dto.setFactor("Tasa");
        dto.setFechaFin("01/01/2021");
        dto.setFechaInicio("01/01/2019");
        dto.setImpuesto("IVA");
        dto.setRetencion(Boolean.FALSE);
        dto.setTipo("Fijo");
        dto.setTraslado(Boolean.TRUE);
        dto.setValorMaximo(new BigDecimal(0.002));
        dto.setValorMinimo(new BigDecimal(0.001));
        return dto;
    }

    @Test
    public void addTasaCuota_successTransaction_responseCreated() throws Exception {
        TasaCuotaDTO resultFromService = mockDTO();
        
        when(service.addTasaCuota(any(TasaCuotaDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<TasaCuotaDTO> responseResult = (ResponseEntity)controller.addTasaCuota(expectedDTO);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.CREATED, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TasaCuotaDTO resultDTO = responseResult.getBody();
        assertEquals(expectedId, resultDTO.getId());
        assertEquals("Tasa", resultDTO.getFactor());
        assertEquals("01/01/2021", resultDTO.getFechaFin());
        assertEquals("01/01/2019", resultDTO.getFechaInicio());
        assertEquals("IVA", resultDTO.getImpuesto());
        assertEquals("Fijo", resultDTO.getTipo());
        assertFalse(resultDTO.getRetencion());
        assertTrue(resultDTO.getTraslado());
        assertEquals(new BigDecimal(0.002), resultDTO.getValorMaximo());
        assertEquals(new BigDecimal(0.001), resultDTO.getValorMinimo());
    }

     @Test
    public void updateTasaCuota_sucessTransaction_responseOK() throws Exception {
        TasaCuotaDTO resultFromService = mockDTO();

        when(service.updateTasaCuota(any(TasaCuotaDTO.class)))
           .thenReturn(resultFromService);
        ResponseEntity<TasaCuotaDTO> responseResult = (ResponseEntity)controller.updateTasaCuota(expectedDTO, expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TasaCuotaDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
        assertEquals("Tasa", resultDTO.getFactor());
        assertEquals("01/01/2021", resultDTO.getFechaFin());
        assertEquals("01/01/2019", resultDTO.getFechaInicio());
        assertEquals("IVA", resultDTO.getImpuesto());
        assertEquals("Fijo", resultDTO.getTipo());
        assertFalse(resultDTO.getRetencion());
        assertTrue(resultDTO.getTraslado());
        assertEquals(new BigDecimal(0.002), resultDTO.getValorMaximo());
        assertEquals(new BigDecimal(0.001), resultDTO.getValorMinimo());
    }

    @Test
    public void findById_serviceReturnsElement_responseOK() throws Exception {
        TasaCuotaDTO resultFromService = mockDTO();
        when(service.findById(eq(expectedId)))
            .thenReturn(resultFromService);

        ResponseEntity<TasaCuotaDTO> responseResult = (ResponseEntity)controller.findById(expectedId);

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        TasaCuotaDTO resultDTO = responseResult.getBody();
        assertEquals(resultDTO.getId(), expectedId);
        assertEquals("Tasa", resultDTO.getFactor());
        assertEquals("01/01/2021", resultDTO.getFechaFin());
        assertEquals("01/01/2019", resultDTO.getFechaInicio());
        assertEquals("IVA", resultDTO.getImpuesto());
        assertEquals("Fijo", resultDTO.getTipo());
        assertFalse(resultDTO.getRetencion());
        assertTrue(resultDTO.getTraslado());
        assertEquals(new BigDecimal(0.002), resultDTO.getValorMaximo());
        assertEquals(new BigDecimal(0.001), resultDTO.getValorMinimo());
    }

    @Test
    public void findAll_serviceReturnsCollection_responseOK() throws Exception {
        TasaCuotaDTO resultFromService = mockDTO();
        when(service.findAll())
            .thenReturn(Collections.singletonList(resultFromService));

        ResponseEntity<Collection<TasaCuotaDTO>>   responseResult = (ResponseEntity)controller.findAll();

        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Collection<TasaCuotaDTO> collectionResult = responseResult.getBody();
        assertFalse(collectionResult.isEmpty());
    }

    @Test
    public void getPaginatedResult_serviceReturnsPage_responseOK() throws Exception {
        when(service.getPaginatedResult(eq(0), eq(10), eq("id"), eq("asc")))
            .thenReturn(new PageImpl(Collections.singletonList(expectedDTO), PageRequest.of(0, 10), 10));
        
        ResponseEntity<Page<TasaCuotaDTO>> responseResult = (ResponseEntity)controller.getPaginatedResult(0, 10, "id", "asc");
        
        assertNotNull(responseResult);
        assertEquals(HttpStatus.OK, responseResult.getStatusCode());
        assertNotNull(responseResult.getBody());

        Page<TasaCuotaDTO> paginatedResult = responseResult.getBody();
        assertEquals(1, paginatedResult.getContent().size());
    }


}
