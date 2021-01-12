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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.conta.cloud.sat.service.impl.RegimenFiscalServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.RegimenFiscalDTO;
import com.conta.cloud.sat.mappers.RegimenFiscalMapper;
import com.conta.cloud.sat.persistence.RegimenFiscalRepository;
import com.conta.cloud.sat.domain.RegimenFiscal;

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

@DisplayName("RegimenFiscal Service Test")
@ExtendWith(MockitoExtension.class)
public class RegimenFiscalServiceTest {

    @Mock
    private RegimenFiscalRepository repository;

    private RegimenFiscalMapper mapper = Mappers.getMapper(RegimenFiscalMapper.class);

    private RegimenFiscalServiceImpl service;

    private RegimenFiscal entity;

    private RegimenFiscalDTO regimenFiscalDTO;

    String id = "test-id";

    @BeforeEach
    public void setup() {
        entity = new RegimenFiscal();
        entity.setId(id);
        entity.setDescripcion("Test Description");
        entity.setFechaFin(DateUtils.fromString("01/01/2021"));
        entity.setFechaInicio(DateUtils.fromString("01/01/2019"));
        entity.setPersonaFisica(Boolean.TRUE);
        entity.setPersonaMoral(Boolean.FALSE);
        
        regimenFiscalDTO = new RegimenFiscalDTO();
        regimenFiscalDTO.setId(id);
        regimenFiscalDTO.setDescripcion("Test Description");
        regimenFiscalDTO.setFechaFin("01/01/2021");
        regimenFiscalDTO.setFechaInicio("01/01/2019");
        regimenFiscalDTO.setPersonaFisica(Boolean.TRUE);
        regimenFiscalDTO.setPersonaMoral(Boolean.FALSE);

        service = new RegimenFiscalServiceImpl(repository, mapper);
    }

    @Test
    public void addRegimenFiscal_successTransaction() throws Exception {
        RegimenFiscalDTO dto = service.addRegimenFiscal(regimenFiscalDTO);
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Test Description", dto.getDescripcion());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertTrue(dto.getPersonaFisica());
        assertFalse(dto.getPersonaMoral());
    }


    @Test
    public void addRegimenFiscal_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(RegimenFiscal.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addRegimenFiscal(regimenFiscalDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(RegimenFiscal.class));
    }

    @Test
    public void addRegimenFiscal_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(RegimenFiscal.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addRegimenFiscal(regimenFiscalDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(RegimenFiscal.class));
    }

    @Test
    public void updateRegimenFiscal_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateRegimenFiscal(regimenFiscalDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateRegimenFiscal_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        RegimenFiscalDTO dto = service.updateRegimenFiscal(regimenFiscalDTO);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Test Description", dto.getDescripcion());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertTrue(dto.getPersonaFisica());
        assertFalse(dto.getPersonaMoral());

        verify(repository).existsById(eq(id));

        verify(repository).save(any(RegimenFiscal.class));
    }

    @Test
    public void updateRegimenFiscal_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateRegimenFiscal(regimenFiscalDTO);
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

        RegimenFiscalDTO dto = service.findById(id);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Test Description", dto.getDescripcion());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertTrue(dto.getPersonaFisica());
        assertFalse(dto.getPersonaMoral());

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

        Collection<RegimenFiscalDTO> dtoList = service.findAll();
        assertEquals(1, dtoList.size());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<RegimenFiscalDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<RegimenFiscalDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<RegimenFiscalDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

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
        
        Page<RegimenFiscalDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

}
