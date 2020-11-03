package com.conta.cloud.sat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;

import com.conta.cloud.sat.domain.Unidad;
import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.mappers.UnidadMapper;
import com.conta.cloud.sat.persistence.UnidadRepository;
import com.conta.cloud.sat.service.impl.UnidadServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Codig Unidad Service Testing")
public class UnidadServiceTest {

    private UnidadServiceImpl unidadService;

    private UnidadMapper mapper = Mappers.getMapper(UnidadMapper.class);

    @Mock
    private UnidadRepository repository;

    Unidad unidad;

    @BeforeEach
    public void setup() {
        unidadService = new UnidadServiceImpl(mapper, repository);
        unidad = new Unidad();
        unidad.setIdUnidad("id-test");
        unidad.setNombre("Metros");
        unidad.setSimbolo("m");
    }

    @Test
    public void findUnidades_emptyParams_returnAllItems() throws Exception {
        when(repository.findAll())
        .thenReturn(Collections.singletonList(unidad));
        Collection<UnidadDTO> unidades = unidadService.findUnidades("", "", "");

        assertFalse(unidades.isEmpty());
        UnidadDTO unidadDTO = unidades.iterator().next();
        assertEquals(unidad.getIdUnidad(), unidadDTO.getId());
        assertEquals(unidad.getNombre(), unidadDTO.getNombre());
        assertEquals(unidad.getSimbolo(), unidadDTO.getSimbolo());
        assertEquals("", unidadDTO.getFechaInicio());
        assertEquals("", unidadDTO.getFechaFin());

        verify(repository).findAll();
    }

    @Test
    public void findUnidades_dateHasInvalidFormat_shouldThrowException() {
        CatalogException e = assertThrows(CatalogException.class, () -> {
			unidadService.findUnidades("", "", "2020-10-09");
		});
		assertEquals(e.getCode(), ErrorCode.BAD_REQUEST);
        assertEquals(e.getCode().getCode(), 400);
        verifyZeroInteractions(repository);
    }

    @Test
    public void findUnidades_repositoryThrowsException_shouldThrowException() {
        when(repository.findAll(ArgumentMatchers.<Specification<Unidad>>any()))
		.thenThrow(new DataAccessException("Error on dabase call") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
        });

        CatalogException e = assertThrows(CatalogException.class, () -> {
			unidadService.findUnidades("metro", "", "10/09/2020");
		});
        
		assertEquals(e.getCode(), ErrorCode.INTERNAL);
        assertEquals(e.getCode().getCode(), 500);
    }

    @Test
    public void findUnidades_repositoryReturnsCollection_shouldReturnCollectionDTO () throws Exception {
        when(repository.findAll(ArgumentMatchers.<Specification<Unidad>>any()))
		.thenReturn(Collections.singletonList(unidad));
        Collection<UnidadDTO> unidades = unidadService.findUnidades("", "m", "");

        assertFalse(unidades.isEmpty());
        UnidadDTO unidadDTO = unidades.iterator().next();
        assertEquals(unidad.getIdUnidad(), unidadDTO.getId());
        assertEquals(unidad.getNombre(), unidadDTO.getNombre());
        assertEquals(unidad.getSimbolo(), unidadDTO.getSimbolo());
        assertEquals("", unidadDTO.getFechaInicio());
        assertEquals("", unidadDTO.getFechaFin());
    }

    @Test
    public void findUnidades_dateIsNull_shouldReturnCollectionDTO () throws Exception {
        when(repository.findAll(ArgumentMatchers.<Specification<Unidad>>any()))
		.thenReturn(Collections.singletonList(unidad));
        Collection<UnidadDTO> unidades = unidadService.findUnidades("", "m", null);

        assertFalse(unidades.isEmpty());
        UnidadDTO unidadDTO = unidades.iterator().next();
        assertEquals(unidad.getIdUnidad(), unidadDTO.getId());
        assertEquals(unidad.getNombre(), unidadDTO.getNombre());
        assertEquals(unidad.getSimbolo(), unidadDTO.getSimbolo());
        assertEquals("", unidadDTO.getFechaInicio());
        assertEquals("", unidadDTO.getFechaFin());
    }
}