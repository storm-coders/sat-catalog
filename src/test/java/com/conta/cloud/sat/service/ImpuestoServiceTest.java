package com.conta.cloud.sat.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.conta.cloud.sat.domain.Impuesto;
import com.conta.cloud.sat.dto.ImpuestoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.mappers.ImpuestoMapper;
import com.conta.cloud.sat.persistence.ImpuestoRepository;
import com.conta.cloud.sat.service.impl.ImpuestoServiceImpl;

import org.mapstruct.factory.Mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.Collection;
import java.util.Collections;

@DisplayName("ImpuestoService testing")
@ExtendWith(MockitoExtension.class)
public class ImpuestoServiceTest {

   @Mock
   private ImpuestoRepository repository;

   private ImpuestoMapper mapper = Mappers.getMapper(ImpuestoMapper.class);

   private ImpuestoServiceImpl service;

   private Impuesto entity;

   @BeforeEach
   public void setup() {
      entity = new Impuesto();
      entity.setIdImpuesto("001");
      entity.setImpuesto("IVA");
      entity.setRetencion(Boolean.TRUE);
      entity.setTraslado(Boolean.FALSE);
      entity.setTipo("Federal");
      entity.setEntidad("HGO");

      service = new ImpuestoServiceImpl(mapper, repository);
   }

   @Test
   public void findAll_repositoryContainsData_collectionIsNotEmpty() throws Exception {
       when(repository.findAll())
         .thenReturn(Collections.singletonList(entity));

       Collection<ImpuestoDTO> impuestos = service.findAll();

       verify(repository).findAll();

       // matching first element
       for(ImpuestoDTO dto : impuestos) {
          assertEquals("001", dto.getId());
	  assertEquals("IVA", dto.getNombre());
	  assertTrue(dto.getRetencion());
	  assertFalse(dto.getTraslado());
	  assertEquals("Federal", dto.getTipo());
	  assertEquals("HGO", dto.getEntidad());
       }
   }

   @Test
   public void findAll_repositoryThrowsException_exceptionShoudlBePropagated() {
      when(repository.findAll())
         .thenThrow(new RuntimeException("exception from repository"));

      CatalogException cte = assertThrows(CatalogException.class, () -> {
         service.findAll();
      });

      verify(repository).findAll();

      assertEquals(ErrorCode.INTERNAL, cte.getCode());
      assertEquals(500, cte.getCode().getCode());
      assertEquals("Error al procesar petición", cte.getCode().getMessage()); 
   }
}
