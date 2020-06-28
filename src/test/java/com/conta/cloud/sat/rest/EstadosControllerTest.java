package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.EstadoDTO;
import com.conta.cloud.sat.service.EstadoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Estado REST Testing")
public class EstadosControllerTest {

	@Mock
	private EstadoService estadoService;
	
	@InjectMocks
	private EstadosController controller;
	
	@Test
	public void testFindAll() throws Exception {
		when(estadoService.findAll())
		.thenReturn(Collections.emptyList());
		
		ResponseEntity<Collection<EstadoDTO>> estados = controller.getEstados("");
		assertNotNull(estados);
		assertEquals(HttpStatus.OK, estados.getStatusCode());
		assertNotNull(estados.getBody());
		assertTrue(estados.getBody().isEmpty());
	}
	
	@Test
	public void testFindByPais() throws Exception {
		when(estadoService.findByPais(eq("MEX")))
		.thenReturn(Collections.emptyList());
		
		ResponseEntity<Collection<EstadoDTO>> estados = controller.getEstados("MEX");
		assertNotNull(estados);
		assertEquals(HttpStatus.OK, estados.getStatusCode());
		assertNotNull(estados.getBody());
		assertTrue(estados.getBody().isEmpty());
	}
	
}
