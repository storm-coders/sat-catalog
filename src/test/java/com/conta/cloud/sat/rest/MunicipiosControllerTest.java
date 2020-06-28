package com.conta.cloud.sat.rest;

import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.MunicipioDTO;
import com.conta.cloud.sat.service.MunicipiosService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Municipios REST Controller Testing")
public class MunicipiosControllerTest {

	@InjectMocks
	private MunicipioController controller;
	
	@Mock
	private MunicipiosService municipiosService;
	
	@Test
	public void testResponse() throws Exception {
		when(municipiosService.findMunicipios(anyString()))
		.thenReturn(Collections.emptyList());
		
		ResponseEntity<Collection<MunicipioDTO>> response = controller.findMunicipios("");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
	}
}
