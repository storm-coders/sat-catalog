package com.conta.cloud.sat.servce;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import org.mapstruct.factory.Mappers;

import com.conta.cloud.sat.domain.FormaPago;
import com.conta.cloud.sat.domain.IncluirPropiedad;
import com.conta.cloud.sat.dto.FormaPagoDTO;
import com.conta.cloud.sat.dto.IncluirPropiedadDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.mappers.FormaPagoMapper;
import com.conta.cloud.sat.persistence.FormaPagoRepository;
import com.conta.cloud.sat.service.impl.FormaPagoServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
@DisplayName("FormaPagoService testing")
public class FormaPagoServiceTest {

   private FormaPagoServiceImpl service;

   @Mock
   private FormaPagoRepository repository;

   private FormaPagoMapper mapper = Mappers.getMapper(FormaPagoMapper.class);

   private FormaPago entity;

   @BeforeEach
   public void setup() {
      	   
      entity = new FormaPago();
      entity.setIdFormaPago("id-test");
      entity.setDescripcion("desc forma pago");
      entity.setBancarizado(IncluirPropiedad.YES);
      entity.setNumeroOperacion(IncluirPropiedad.NO);
      entity.setRfcCtaOrdenante(IncluirPropiedad.OPT);
      entity.setCtaOrdenante(IncluirPropiedad.OPT);
      entity.setRegexCtaOrdenante("[a-z]*");
      entity.setRfcCtaBeneficiario(IncluirPropiedad.NO);
      entity.setRegexCtaBeneficiario("[0-9]{15}");
      entity.setTipoCadena(IncluirPropiedad.YES);
      entity.setFechaInicio(new Date());
      entity.setFechaFin(new Date());
      
      service = new FormaPagoServiceImpl(mapper, repository);
      
   }

   @Test
   public void findAll_repositoryContainsElements_collectionsIsNotEmpty() throws Exception {
      when(repository.findAll())
         .thenReturn(Collections.singletonList(entity));

      Collection<FormaPagoDTO> formasPago = service.findAll();

      // collection contains elements
      assertFalse(formasPago.isEmpty());

      // repository should be called
      verify(repository).findAll();

      for(FormaPagoDTO dto : formasPago) {
         assertEquals("id-test", dto.getId());
	 assertEquals("desc forma pago", dto.getDescripcion());
	 assertEquals(IncluirPropiedadDTO.YES, dto.getBancarizado());
	 assertEquals(IncluirPropiedadDTO.NO, dto.getNumeroOperacion());
	 assertEquals(IncluirPropiedadDTO.OPT, dto.getRfcCtaOrdenante());
	 assertEquals(IncluirPropiedadDTO.OPT, dto.getCtaOrdenante());
	 assertEquals("[a-z]*", dto.getRegexCtaOrdenante());
	 assertEquals(IncluirPropiedadDTO.NO, dto.getRfcCtaBeneficiario());
	 assertEquals("[0-9]{15}", dto.getRegexCtaBeneficiario());
	 assertEquals(IncluirPropiedadDTO.YES, dto.getTipoCadena());
	 assertNotNull(dto.getFechaInicio());
	 assertNotNull(dto.getFechaFin());
      }
   }

   @Test
   public void findAll_repositoryThrowsException_exceptionShouldBePropagated() {
      
      when(repository.findAll())
         .thenThrow(new RuntimeException());
      CatalogException cte = assertThrows(CatalogException.class , () -> {
         service.findAll();
      });

      assertNotNull(cte);
   }
}
