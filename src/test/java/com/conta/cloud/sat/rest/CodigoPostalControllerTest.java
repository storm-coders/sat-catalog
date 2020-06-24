package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.CodigoPostalDTO;
import com.conta.cloud.sat.service.CodigoPostalService;

import exception.CatalogException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Codig Postal Controller Testing")
public class CodigoPostalControllerTest {
	
	@InjectMocks
	private CodigoPostalController codigoPostalController;
	
	@Mock
	private CodigoPostalService codigoPostalService;
	
	@BeforeEach
	public void setup() throws CatalogException {
		when(codigoPostalService.findCodigoPostal(anyString(), anyString(), anyString()))
		.thenReturn(Collections.emptyList());
	}
	
	@Test
	public void testFindCodigoPostal_Success() throws Exception {
		ResponseEntity<Collection<CodigoPostalDTO>> response = codigoPostalController.findCodigo("HID", "007", "46000");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
