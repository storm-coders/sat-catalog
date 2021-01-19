package com.conta.cloud.sat.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.conta.cloud.sat.service.impl.TipoRelacionServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TipoRelacionDTO;
import com.conta.cloud.sat.mappers.TipoRelacionMapper;
import com.conta.cloud.sat.persistence.TipoRelacionRepository;
import com.conta.cloud.sat.domain.TipoRelacion;

import org.mapstruct.factory.Mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Collections;

@DisplayName("TipoRelacion Service Testing")
@ExtendWith(MockitoExtension.class)
public class TipoRelacionServiceTest {

    @Mock
    private TipoRelacionRepository repository;

    private TipoRelacionMapper mapper = Mappers.getMapper(TipoRelacionMapper.class);

    private TipoRelacionServiceImpl service;

    private TipoRelacion entity;

    private TipoRelacionDTO tipoRelacionDTO;

    String id = "test-id";

    @BeforeEach
    public void setup() {
        entity = new TipoRelacion();
        entity.setId(id);
        entity.setDescripcion("Nota de Credito");
        entity.setFechaInicio(DateUtils.fromString("01/01/2019"));
        entity.setFechaFin(DateUtils.fromString("01/01/2021"));
        
        tipoRelacionDTO = new TipoRelacionDTO();
        tipoRelacionDTO.setId(id);
        tipoRelacionDTO.setDescripcion("Nota de Credito");
        tipoRelacionDTO.setFechaInicio("01/01/2019");
        tipoRelacionDTO.setFechaFin("01/01/2021");
        service = new TipoRelacionServiceImpl(repository, mapper);
    }

    private void assertDTOProperties(TipoRelacionDTO dto) {
        assertEquals(id, dto.getId());
        assertEquals(tipoRelacionDTO.getDescripcion(), dto.getDescripcion());
        assertEquals(tipoRelacionDTO.getFechaInicio(), dto.getFechaInicio());
        assertEquals(tipoRelacionDTO.getFechaFin(), dto.getFechaFin());
    }

    @Test
    public void addTipoRelacion_successTransaction() throws Exception {
        TipoRelacionDTO dto = service.addTipoRelacion(tipoRelacionDTO);
        assertNotNull(dto);
    }


    @Test
    public void addTipoRelacion_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(TipoRelacion.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addTipoRelacion(tipoRelacionDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(TipoRelacion.class));
    }

    @Test
    public void addTipoRelacion_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(TipoRelacion.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addTipoRelacion(tipoRelacionDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(TipoRelacion.class));
    }

    @Test
    public void updateTipoRelacion_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateTipoRelacion(tipoRelacionDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateTipoRelacion_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        TipoRelacionDTO dto = service.updateTipoRelacion(tipoRelacionDTO);

        assertNotNull(dto);
        assertDTOProperties(dto);

        verify(repository).existsById(eq(id));

        verify(repository).save(any(TipoRelacion.class));
    }

    @Test
    public void updateTipoRelacion_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateTipoRelacion(tipoRelacionDTO);
        });

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void findById_entityNotExists_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.findById(id);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void findById_errorFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed error"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.findById(id);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void findById_entityIsInDB_DTOIsReturned() throws Exception {
        when(repository.existsById(eq(id)))
            .thenReturn(Boolean.TRUE);

        when(repository.getOne(eq(id)))
            .thenReturn(entity);

        TipoRelacionDTO dto = service.findById(id);

        assertNotNull(dto);
        assertDTOProperties(dto);

        verify(repository).existsById(eq(id));

        verify(repository).getOne(eq(id));
    }

    @Test
    public void findAll_exceptionIsThrown_customExceptionShouldBeThrown() throws Exception {

        when(repository.findAll())
            .thenThrow(new RuntimeException("Invalid transaction"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.findAll();
        });

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
    }

    @Test
    public void findAll_successTransaction_dtoListIsReturned() throws Exception {
        when(repository.findAll())
            .thenReturn(Collections.singletonList(entity));

        Collection<TipoRelacionDTO> dtoList = service.findAll();
        assertEquals(1, dtoList.size());
        assertDTOProperties(dtoList.iterator().next());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoRelacionDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoRelacionDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoRelacionDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_invalidParametersProvided_customExceptionIsThrown () throws Exception {        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            Page<TipoRelacionDTO> dtoPage = service.getPaginatedResult(0, 10, "invalidColumn", "invalidOrder");
        });
        

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
        verifyNoInteractions(repository);
    }

    @Test
    public void getPaginatedResult_orderIsNull_defaultSortIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoRelacionDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

}
