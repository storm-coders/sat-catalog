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

import com.conta.cloud.sat.service.impl.TipoComprobanteServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TipoComprobanteDTO;
import com.conta.cloud.sat.mappers.TipoComprobanteMapper;
import com.conta.cloud.sat.persistence.TipoComprobanteRepository;
import com.conta.cloud.sat.domain.TipoComprobante;

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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@DisplayName("TipoComprobante Service Testing")
@ExtendWith(MockitoExtension.class)
public class TipoComprobanteServiceTest {

    @Mock
    private TipoComprobanteRepository repository;

    private TipoComprobanteMapper mapper = Mappers.getMapper(TipoComprobanteMapper.class);

    private TipoComprobanteServiceImpl service;

    private TipoComprobante entity;

    private TipoComprobanteDTO tipoComprobanteDTO;

    String id = "test-id";

    @BeforeEach
    public void setup() {
        entity = new TipoComprobante();
        entity.setId(id);
        entity.setDescripcion("Ingreso");
        entity.setFechaFin(DateUtils.fromString("01/01/2021"));
        entity.setFechaInicio(DateUtils.fromString("01/01/2019"));
        entity.setValorMaximo(new BigDecimal("999999999999999999.9999990000"));
        
        tipoComprobanteDTO = new TipoComprobanteDTO();
        tipoComprobanteDTO.setId(id);
        tipoComprobanteDTO.setDescripcion("Ingreso");
        tipoComprobanteDTO.setFechaFin("01/01/2021");
        tipoComprobanteDTO.setFechaInicio("01/01/2019");
        tipoComprobanteDTO.setValorMaximo(new BigDecimal("999999999999999999.9999990000"));
        service = new TipoComprobanteServiceImpl(repository, mapper);
    }

    private void assertDTOProps(TipoComprobanteDTO dto) {
        assertEquals(id, dto.getId());
        assertEquals("Ingreso", dto.getDescripcion());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals(new BigDecimal("999999999999999999.9999990000"), dto.getValorMaximo());
    } 

    @Test
    public void addTipoComprobante_successTransaction() throws Exception {
        TipoComprobanteDTO dto = service.addTipoComprobante(tipoComprobanteDTO);
        assertNotNull(dto);
        assertDTOProps(dto);
    }


    @Test
    public void addTipoComprobante_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(TipoComprobante.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addTipoComprobante(tipoComprobanteDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(TipoComprobante.class));
    }

    @Test
    public void addTipoComprobante_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(TipoComprobante.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addTipoComprobante(tipoComprobanteDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(TipoComprobante.class));
    }

    @Test
    public void updateTipoComprobante_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateTipoComprobante(tipoComprobanteDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateTipoComprobante_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        TipoComprobanteDTO dto = service.updateTipoComprobante(tipoComprobanteDTO);

        assertNotNull(dto);
        assertDTOProps(dto);

        verify(repository).existsById(eq(id));

        verify(repository).save(any(TipoComprobante.class));
    }

    @Test
    public void updateTipoComprobante_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateTipoComprobante(tipoComprobanteDTO);
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

        TipoComprobanteDTO dto = service.findById(id);

        assertNotNull(dto);
        assertDTOProps(dto);

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

        Collection<TipoComprobanteDTO> dtoList = service.findAll();
        assertEquals(1, dtoList.size());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoComprobanteDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProps(dtoPage.getContent().get(0));
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoComprobanteDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TipoComprobanteDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProps(dtoPage.getContent().get(0));
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
        
        Page<TipoComprobanteDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        assertDTOProps(dtoPage.getContent().get(0));
    }

}
