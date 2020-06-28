package com.conta.cloud.sat.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.conta.cloud.sat.domain.Municipio;
import com.conta.cloud.sat.dto.MunicipioDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.mappers.MunicipioMapper;
import com.conta.cloud.sat.persistence.MunicipiosRepository;
import com.conta.cloud.sat.service.impl.MunicipioServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Municipio Service Testing")
public class MunicipioServiceTest {
	
	private MunicipioServiceImpl municipioServiceImpl;
	
	@Mock
	private MunicipiosRepository repository;
	
	private MunicipioMapper mapper = Mappers.getMapper(MunicipioMapper.class);
	private List<Municipio> response;
	@BeforeEach
	public void setup() {
		municipioServiceImpl = new MunicipioServiceImpl(repository, mapper);
		
		response = Collections.singletonList(
				new Municipio("077-546-as", "077", "Pachuca de Soto", "HID")
				);
	}
	
	@Test
	public void testFindAll() throws Exception {
		when(repository.findAll())
		.thenReturn(response);
		Collection<MunicipioDTO> municipios = municipioServiceImpl.findMunicipios(null);
		
		for (MunicipioDTO municipioDTO : municipios) {
			assertEquals("077-546-as", municipioDTO.getId());
			assertEquals("077", municipioDTO.getCodigo());
			assertEquals("Pachuca de Soto", municipioDTO.getNombre());
			assertEquals("HID", municipioDTO.getIdEstado());
		}
	}
	
	@Test
	public void testFindByIdEstado() throws Exception {
		when(repository.findByIdEstado("HID"))
		.thenReturn(response);
		Collection<MunicipioDTO> municipios = municipioServiceImpl.findMunicipios("HID");
		
		for (MunicipioDTO municipioDTO : municipios) {
			assertEquals("077-546-as", municipioDTO.getId());
			assertEquals("077", municipioDTO.getCodigo());
			assertEquals("Pachuca de Soto", municipioDTO.getNombre());
			assertEquals("HID", municipioDTO.getIdEstado());
		}
	}
	
	@Test
	public void testException() throws Exception {
		when(repository.findAll())
		.thenThrow(new DataAccessException("Error in DB query") {
		});
		CatalogException cta = assertThrows(CatalogException.class, () -> municipioServiceImpl.findMunicipios(null));
		
		assertEquals(ErrorCode.INTERNAL, cta.getCode());
		assertFalse(cta.getErrors().isEmpty());
		assertEquals(CatalogExceptionConstants.INTERNAL_CODE, cta.getErrors().get(0).getCode());
		assertEquals(MunicipiosService.INTERNAL_ERROR_MESSAGE, cta.getErrors().get(0).getReason());
	}

}
