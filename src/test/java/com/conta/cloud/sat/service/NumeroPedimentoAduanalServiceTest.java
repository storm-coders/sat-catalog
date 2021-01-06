package com.conta.cloud.sat.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.conta.cloud.sat.service.impl.NumeroPedimentoAduanalServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.NumeroPedimentoAduanalDTO;
import com.conta.cloud.sat.mappers.NumeroPedimentoAduanalMapper;
import com.conta.cloud.sat.persistence.NumeroPedimentoAduanalRepository;
import com.conta.cloud.sat.domain.NumeroPedimentoAduanal;

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

@DisplayName("NumeroPedimentoAduanal Service Test")
@ExtendWith(MockitoExtension.class)
public class NumeroPedimentoAduanalServiceTest {

    @Mock
    private NumeroPedimentoAduanalRepository repository;

    private NumeroPedimentoAduanalMapper mapper = Mappers.getMapper(NumeroPedimentoAduanalMapper.class);

    private NumeroPedimentoAduanalServiceImpl service;

    private NumeroPedimentoAduanal entity;

    private NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO;

    String id = "test-id";

    @BeforeEach
    public void setup() {
        entity = new NumeroPedimentoAduanal();
        entity.setId(id);
        entity.setCantidad(100);
        entity.setEjercicio(2020);
        entity.setFechaFin(DateUtils.fromString("01/01/2021"));
        entity.setFechaInicio(DateUtils.fromString("01/01/2019"));
        entity.setIdAduana("01");
        entity.setPatente("2001");
        
        numeroPedimentoAduanalDTO = new NumeroPedimentoAduanalDTO();
        numeroPedimentoAduanalDTO.setId(id);
        numeroPedimentoAduanalDTO.setCantidad(100);
        numeroPedimentoAduanalDTO.setEjercicio(2020);
        numeroPedimentoAduanalDTO.setFechaFin("01/01/2021");
        numeroPedimentoAduanalDTO.setFechaInicio("01/01/2019");
        numeroPedimentoAduanalDTO.setIdAduana("01");
        numeroPedimentoAduanalDTO.setPatente("2001");
        service = new NumeroPedimentoAduanalServiceImpl(repository, mapper);
    }

    @Test
    public void addNumeroPedimentoAduanal_successTransaction() throws Exception {
        NumeroPedimentoAduanalDTO dto = service.addNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(100, dto.getCantidad());
        assertEquals(2020, dto.getEjercicio());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("01", dto.getIdAduana());
        assertEquals("2001", dto.getPatente());
    }


    @Test
    public void addNumeroPedimentoAduanal_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(NumeroPedimentoAduanal.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(NumeroPedimentoAduanal.class));
    }

    @Test
    public void addNumeroPedimentoAduanal_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(NumeroPedimentoAduanal.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(NumeroPedimentoAduanal.class));
    }

    @Test
    public void updateNumeroPedimentoAduanal_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateNumeroPedimentoAduanal_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        NumeroPedimentoAduanalDTO dto = service.updateNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(100, dto.getCantidad());
        assertEquals(2020, dto.getEjercicio());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("01", dto.getIdAduana());
        assertEquals("2001", dto.getPatente());

        verify(repository).existsById(eq(id));

        verify(repository).save(any(NumeroPedimentoAduanal.class));
    }

    @Test
    public void updateNumeroPedimentoAduanal_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateNumeroPedimentoAduanal(numeroPedimentoAduanalDTO);
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

        NumeroPedimentoAduanalDTO dto = service.findById(id);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(100, dto.getCantidad());
        assertEquals(2020, dto.getEjercicio());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("01", dto.getIdAduana());
        assertEquals("2001", dto.getPatente());

        verify(repository).existsById(eq(id));

        verify(repository).getOne(eq(id));
    }

    @Test
    public void findAll_exceptionIsThrown_customExceptionShouldBeThrown() throws Exception {

        when(repository.findAll())
            .thenThrow(new RuntimeException("Invalid transaction"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.findAll("");
        });

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
    }

    @Test
    public void findAll_successTransaction_dtoListIsReturned() throws Exception {
        when(repository.findAll())
            .thenReturn(Collections.singletonList(entity));

        Collection<NumeroPedimentoAduanalDTO> dtoList = service.findAll("");
        assertEquals(1, dtoList.size());
    }

    @Test
    public void findAll_filterIsPresent_dtoListIsReturned() throws Exception {
        when(repository.findByIdAduana(eq("01")))
            .thenReturn(Collections.singletonList(entity));

        Collection<NumeroPedimentoAduanalDTO> dtoList = service.findAll("01");
        assertEquals(1, dtoList.size());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<NumeroPedimentoAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<NumeroPedimentoAduanalDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<NumeroPedimentoAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_invalidParametersProvided_customExceptionIsThrown () throws Exception {        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            Page<NumeroPedimentoAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, "invalidColumn", "invalidOrder");
        });
        

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
        verifyNoInteractions(repository);
    }

    @Test
    public void getPaginatedResult_orderIsNull_defaultSortIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<NumeroPedimentoAduanalDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

}
