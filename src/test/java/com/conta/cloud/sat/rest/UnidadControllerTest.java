package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.service.UnidadService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Paises REST Testing")
public class UnidadControllerTest {

    @Mock
    private UnidadService service;

    @InjectMocks
    private UnidadController controller;

    @Test
    public void testFindUnidades() throws Exception {
        when(service.findUnidades(anyString(), anyString(), anyString()))
        .thenReturn(Collections.emptyList());

        ResponseEntity<Collection<UnidadDTO>> unidades = controller.findUnidades("", "", "");
		assertNotNull(unidades);
		assertEquals(HttpStatus.OK, unidades.getStatusCode());
		assertNotNull(unidades.getBody());
		assertTrue(unidades.getBody().isEmpty());
    }
}