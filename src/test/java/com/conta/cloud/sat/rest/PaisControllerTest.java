package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.PaisDTO;
import com.conta.cloud.sat.service.PaisService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Paises REST Testing")
public class PaisControllerTest {

	@Mock
	private PaisService paisService;
	
	@InjectMocks
	private PaisController controller;
	
	@Test
	public void testFindAll() throws Exception {
		when(paisService.findPaises())
		.thenReturn(Collections.emptyList());
		
		ResponseEntity<Collection<PaisDTO>> paises = controller.findPaises();
		assertNotNull(paises);
		assertEquals(HttpStatus.OK, paises.getStatusCode());
		assertNotNull(paises.getBody());
		assertTrue(paises.getBody().isEmpty());
	}
	
}

