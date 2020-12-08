package com.conta.cloud.sat.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.mockito.Mock;
import org.mockito.InjectMocks;

import com.conta.cloud.sat.dto.ImpuestoDTO;
import com.conta.cloud.sat.service.ImpuestoService;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImpuestoController testing")
public class ImpuestoControllerTest {
   @Mock
   private ImpuestoService service;

   @InjectMocks
   private ImpuestoController controller;

   @Test
   public void findAll_successResponse() throws Exception {
      ResponseEntity<Collection<ImpuestoDTO>> dtos = controller.findAll();
      assertNotNull(dtos);
   }
}
