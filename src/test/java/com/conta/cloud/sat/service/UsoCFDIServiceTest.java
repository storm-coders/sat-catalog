package com.conta.cloud.sat.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.conta.cloud.sat.service.impl.UsoCFDIServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.UsoCFDIDTO;
import com.conta.cloud.sat.mappers.UsoCFDIMapper;
import com.conta.cloud.sat.persistence.UsoCFDIRepository;
import com.conta.cloud.sat.domain.UsoCFDI;

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

@DisplayName("UsoCFDI Service Testing")
@ExtendWith(MockitoExtension.class)
public class UsoCFDIServiceTest {

    @Mock
    private UsoCFDIRepository repository;

    private UsoCFDIMapper mapper = Mappers.getMapper(UsoCFDIMapper.class);

    private UsoCFDIServiceImpl service;

    private UsoCFDI entity;

    private UsoCFDIDTO usoCFDIDTO;

    String id = "G03";

    @BeforeEach
    public void setup() {
        entity = new UsoCFDI();
        entity.setId(id);
        entity.setDescripcion("Gastos en general");
        entity.setFechaInicio(DateUtils.fromString("01/01/2017"));
        entity.setPersonaFisica(Boolean.TRUE);
        entity.setPersonaMoral(Boolean.TRUE);
        
        usoCFDIDTO = new UsoCFDIDTO();
        usoCFDIDTO.setId(id);
        usoCFDIDTO.setDescripcion("Gastos en general");
        usoCFDIDTO.setPersonaFisica(Boolean.TRUE);
        usoCFDIDTO.setPersonaMoral(Boolean.TRUE);
        usoCFDIDTO.setFechaInicio("01/01/2017");

        service = new UsoCFDIServiceImpl(repository, mapper);
    }

    private void assertDTOProperties(UsoCFDIDTO dto) {
        assertEquals(id, dto.getId());
        assertEquals("Gastos en general", dto.getDescripcion());
        assertEquals("01/01/2017", dto.getFechaInicio());
        assertEquals("", dto.getFechaFin());
        assertTrue(dto.getPersonaFisica());
        assertTrue(dto.getPersonaMoral());
    }

    @Test
    public void addUsoCFDI_successTransaction() throws Exception {
        UsoCFDIDTO dto = service.addUsoCFDI(usoCFDIDTO);
        assertNotNull(dto);
        assertDTOProperties(dto);
    }


    @Test
    public void addUsoCFDI_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(UsoCFDI.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addUsoCFDI(usoCFDIDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(UsoCFDI.class));
    }

    @Test
    public void addUsoCFDI_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(UsoCFDI.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addUsoCFDI(usoCFDIDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(UsoCFDI.class));
    }

    @Test
    public void updateUsoCFDI_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateUsoCFDI(usoCFDIDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateUsoCFDI_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        UsoCFDIDTO dto = service.updateUsoCFDI(usoCFDIDTO);

        assertNotNull(dto);
        assertDTOProperties(dto);

        verify(repository).existsById(eq(id));

        verify(repository).save(any(UsoCFDI.class));
    }

    @Test
    public void updateUsoCFDI_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateUsoCFDI(usoCFDIDTO);
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

        UsoCFDIDTO dto = service.findById(id);

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

        Collection<UsoCFDIDTO> dtoList = service.findAll();
        assertEquals(1, dtoList.size());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<UsoCFDIDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<UsoCFDIDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<UsoCFDIDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_invalidParametersProvided_customExceptionIsThrown () throws Exception {        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            Page<UsoCFDIDTO> dtoPage = service.getPaginatedResult(0, 10, "invalidColumn", "invalidOrder");
        });
        

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
        verifyNoInteractions(repository);
    }

    @Test
    public void getPaginatedResult_orderIsNull_defaultSortIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<UsoCFDIDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProperties(dtoPage.getContent().get(0));
    }

}
