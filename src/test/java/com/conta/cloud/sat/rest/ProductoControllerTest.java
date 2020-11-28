package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.conta.cloud.sat.dto.ProductoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.service.ProductoService;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("Producto Controller testing")
public class ProductoControllerTest {	
   
   @InjectMocks	
   private ProductoController controller;

   @Mock
   private ProductoService service;

   @Test
   public void findProductos_exceptionIsThrown_exceptionShouldBePropagated() throws Exception {
   	when(service.findProductos(anyString(), anyString()))
		.thenThrow(new CatalogException());
	CatalogException exception = assertThrows(CatalogException.class, () -> {
	   controller.findProductos("", "");
	});
	verify(service).findProductos(eq(""), eq(""));
	assertNull(exception.getCode());
   }

   @Test
   public void findProductos_successResponse() throws Exception {
      when(service.findProductos(eq("test-id"), eq("test-desc")))
	.thenReturn(Collections.emptyList());

      ResponseEntity<Collection<ProductoDTO>> responseEntity = controller.findProductos("test-id", "test-desc");

      verify(service).findProductos(eq("test-id"), eq("test-desc"));
      assertNotNull(responseEntity);
      assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      assertNotNull(responseEntity.getBody());
      assertTrue(responseEntity.getBody().isEmpty());
   }
}
