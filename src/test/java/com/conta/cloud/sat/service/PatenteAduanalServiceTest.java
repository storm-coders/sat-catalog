package com.conta.cloud.sat.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.conta.cloud.sat.service.impl.PatenteAduanalServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.PatenteAduanalDTO;
import com.conta.cloud.sat.mappers.PatenteAduanalMapper;
import com.conta.cloud.sat.persistence.PatenteAduanalRepository;
import com.conta.cloud.sat.domain.PatenteAduanal;

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

@DisplayName("PatenteAduanal Service Testing")
@ExtendWith(MockitoExtension.class)
public class PatenteAduanalServiceTest {

    @Mock
    private PatenteAduanalRepository repository;

    private PatenteAduanalMapper mapper = Mappers.getMapper(PatenteAduanalMapper.class);

    private PatenteAduanalServiceImpl service;

    private PatenteAduanal entity;

    private PatenteAduanalDTO patenteAduanalDTO;

    String id = "test-id";

    @BeforeEach
    public void setup() {
        entity = new PatenteAduanal();
        entity.setId(id);
        entity.setFechaFin(DateUtils.fromString("01/01/2021"));
        entity.setFechaInicio(DateUtils.fromString("01/01/2019"));
        
        patenteAduanalDTO = new PatenteAduanalDTO();
        patenteAduanalDTO.setId(id);
        patenteAduanalDTO.setFechaFin("01/01/2021");
        patenteAduanalDTO.setFechaInicio("01/01/2019");
        service = new PatenteAduanalServiceImpl(repository, mapper);
    }

    @Test
    public void addPatenteAduanal_successTransaction() throws Exception {
        PatenteAduanalDTO dto = service.addPatenteAduanal(patenteAduanalDTO);
        assertNotNull(dto);
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
    }


    @Test
    public void addPatenteAduanal_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(PatenteAduanal.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addPatenteAduanal(patenteAduanalDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(PatenteAduanal.class));
    }

    @Test
    public void addPatenteAduanal_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(PatenteAduanal.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addPatenteAduanal(patenteAduanalDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(PatenteAduanal.class));
    }

    @Test
    public void updatePatenteAduanal_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updatePatenteAduanal(patenteAduanalDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updatePatenteAduanal_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        PatenteAduanalDTO dto = service.updatePatenteAduanal(patenteAduanalDTO);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());

        verify(repository).existsById(eq(id));

        verify(repository).save(any(PatenteAduanal.class));
    }

    @Test
    public void updatePatenteAduanal_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updatePatenteAduanal(patenteAduanalDTO);
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

        PatenteAduanalDTO dto = service.findById(id);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());

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

        Collection<PatenteAduanalDTO> dtoList = service.findAll();
        assertEquals(1, dtoList.size());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<PatenteAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<PatenteAduanalDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<PatenteAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_invalidParametersProvided_customExceptionIsThrown () throws Exception {        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.getPaginatedResult(0, 10, "invalidColumn", "invalidOrder");
        });
        

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
        verifyNoInteractions(repository);
    }

    @Test
    public void getPaginatedResult_orderIsNull_defaultSortIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<PatenteAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

}
