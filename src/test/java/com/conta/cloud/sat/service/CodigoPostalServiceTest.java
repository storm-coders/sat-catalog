package com.conta.cloud.sat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;

import com.conta.cloud.sat.domain.CodigoPostal;
import com.conta.cloud.sat.domain.CodigoPostalId;
import com.conta.cloud.sat.dto.CodigoPostalDTO;
import com.conta.cloud.sat.mappers.CodigoPostalMapper;
import com.conta.cloud.sat.persistence.CodigoPostalRepository;
import com.conta.cloud.sat.service.impl.CodigoPostalServiceImpl;

import exception.CatalogException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Codig Postal Service Testing")
public class CodigoPostalServiceTest {
	
	@InjectMocks
	private CodigoPostalServiceImpl codigoPostalService;
	
	private CodigoPostalMapper mapper = Mappers.getMapper(CodigoPostalMapper.class);
	private CodigoPostal codigoPostal;
	
	@Mock
	private CodigoPostalRepository codigoPostalRepository;
	
	@BeforeEach
	public void setup() {
		codigoPostalService = new CodigoPostalServiceImpl(codigoPostalRepository, mapper);
		codigoPostal = new CodigoPostal(new CodigoPostalId("43600", "HID"),
				"007", false, new Date(), null);
	}
	
	@Test
	public void findCodigoPostal_InvalidParams() {
		when(codigoPostalRepository.findAll(ArgumentMatchers.<Specification<CodigoPostal>>any()))
		.thenThrow(new DataAccessException("Error on dabase call") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
		});
		CatalogException e = assertThrows(CatalogException.class, () -> {
			codigoPostalService.findCodigoPostal("HID", null, "43600");
		});
		assertEquals(e.getCode(), ErrorCode.INTERNAL);
		assertEquals(e.getCode().getCode(), 500);
	}
	
	@Test
	public void findCodigoPostal() throws Exception {
		when(codigoPostalRepository.findAll(ArgumentMatchers.<Specification<CodigoPostal>>any()))
		.thenReturn(Collections.singletonList(codigoPostal));
		
		Collection<CodigoPostalDTO> dtos = codigoPostalService.findCodigoPostal("HID", "007", "43600");
		
		dtos.forEach(dto -> {
			assertNotNull(dto.getCodigoMunicipio());
			assertFalse(dto.getEstimuloEnFrontera());
			assertNotNull(dto.getFechaInicio());
			assertNull(dto.getFechaFin());
			assertEquals("43600", dto.getId());
			assertEquals("HID", dto.getIdEstado());
		});
	}
}
