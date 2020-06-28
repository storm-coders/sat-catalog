package com.conta.cloud.sat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.conta.cloud.sat.domain.Estado;
import com.conta.cloud.sat.dto.EstadoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.mappers.EstadoMapper;
import com.conta.cloud.sat.persistence.EstadoRepository;
import com.conta.cloud.sat.service.impl.EstadoServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Estado Service Testing")
public class EstadoServiceTest {
	
	private EstadoServiceImpl estadoServiceImpl;
	
	@Mock
	private EstadoRepository repository;
	
	private EstadoMapper mapper = Mappers.getMapper(EstadoMapper.class);
	
	@BeforeEach
	public void setup() {
		estadoServiceImpl = new EstadoServiceImpl(repository, mapper);
	}
	
	@Test
	public void testFindAllException() throws Exception {
		when(repository.findAll())
		.thenThrow(new DataAccessException("Error on DB call") {
			private static final long serialVersionUID = -7688682312461511777L;
		});
		
		CatalogException cte = assertThrows(CatalogException.class, estadoServiceImpl::findAll);
		assertEquals(ErrorCode.INTERNAL, cte.getCode());
		assertFalse(cte.getErrors().isEmpty());
	}
	
	@Test
	public void testFindByPaisException() throws Exception {
		when(repository.findByIdPais(anyString()))
		.thenThrow(new DataAccessException("Error on DB call") {
			private static final long serialVersionUID = -7688682312461511777L;
		});
		
		CatalogException cte = assertThrows(CatalogException.class,() -> estadoServiceImpl.findByPais("MX"));
		assertEquals(ErrorCode.INTERNAL, cte.getCode());
		assertFalse(cte.getErrors().isEmpty());
	}
	
	@Test
	public void findAll() throws Exception {
		when(repository.findAll())
		.thenReturn(Collections.singletonList(new Estado("HID", "Hidalgo", "MEX")));
		
		Collection<EstadoDTO> estados = estadoServiceImpl.findAll();
		
		for (EstadoDTO estadoDTO : estados) {
			assertEquals("HID", estadoDTO.getId());
			assertEquals("Hidalgo", estadoDTO.getNombre());
			assertEquals("MEX", estadoDTO.getIdPais());
		}
	}
	
	@Test
	public void findByPais() throws Exception {
		when(repository.findByIdPais(anyString()))
		.thenReturn(Collections.singletonList(new Estado("HID", "Hidalgo", "MEX")));
		
		Collection<EstadoDTO> estados = estadoServiceImpl.findByPais("MEX");
		
		for (EstadoDTO estadoDTO : estados) {
			assertEquals("HID", estadoDTO.getId());
			assertEquals("Hidalgo", estadoDTO.getNombre());
			assertEquals("MEX", estadoDTO.getIdPais());
		}
	}
}
