package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.mockito.Mock;
import org.mockito.InjectMocks;

import com.conta.cloud.sat.service.FormaPagoService;
import com.conta.cloud.sat.dto.FormaPagoDTO;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("FormaPagoController testing")
public class FormaPagoControllerTest {

   @Mock
   private FormaPagoService formaPagoService;

   @InjectMocks
   private FormaPagoController controller;

   @Test
   public void findAll_successResponse() throws Exception {
   	ResponseEntity<Collection<FormaPagoDTO>> entity = controller.findAll();
	assertNotNull(entity);
   }
}
