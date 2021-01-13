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

import com.conta.cloud.sat.service.impl.TasaCuotaServiceImpl;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TasaCuotaDTO;
import com.conta.cloud.sat.mappers.TasaCuotaMapper;
import com.conta.cloud.sat.persistence.TasaCuotaRepository;
import com.conta.cloud.sat.domain.TasaCuota;

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

@DisplayName("TasaCuota Service Testing")
@ExtendWith(MockitoExtension.class)
public class TasaCuotaServiceTest {

    @Mock
    private TasaCuotaRepository repository;

    private TasaCuotaMapper mapper = Mappers.getMapper(TasaCuotaMapper.class);

    private TasaCuotaServiceImpl service;

    private TasaCuota entity;

    private TasaCuotaDTO tasaCuotaDTO;

    String id = "test-id";

    @BeforeEach
    public void setup() {
        entity = new TasaCuota();
        entity.setId(id);
        entity.setFactor("Tasa");
        entity.setFechaFin(DateUtils.fromString("01/01/2021"));
        entity.setFechaInicio(DateUtils.fromString("01/01/2019"));
        entity.setImpuesto("IVA");
        entity.setRetencion(Boolean.FALSE);
        entity.setTipo("Fijo");
        entity.setTraslado(Boolean.TRUE);
        entity.setValorMaximo(new BigDecimal(0.002));
        entity.setValorMinimo(new BigDecimal(0.001));
        
        tasaCuotaDTO = new TasaCuotaDTO();
        tasaCuotaDTO.setId(id);
        tasaCuotaDTO.setFactor("Tasa");
        tasaCuotaDTO.setFechaFin("01/01/2021");
        tasaCuotaDTO.setFechaInicio("01/01/2019");
        tasaCuotaDTO.setImpuesto("IVA");
        tasaCuotaDTO.setRetencion(Boolean.FALSE);
        tasaCuotaDTO.setTipo("Fijo");
        tasaCuotaDTO.setTraslado(Boolean.TRUE);
        tasaCuotaDTO.setValorMaximo(new BigDecimal(0.002));
        tasaCuotaDTO.setValorMinimo(new BigDecimal(0.001));

        service = new TasaCuotaServiceImpl(repository, mapper);
    }

    @Test
    public void addTasaCuota_successTransaction() throws Exception {
        TasaCuotaDTO dto = service.addTasaCuota(tasaCuotaDTO);
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());
    }


    @Test
    public void addTasaCuota_dataIntegrityExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(TasaCuota.class)))
            .thenThrow(new DataIntegrityViolationException("Integrity violation"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addTasaCuota(tasaCuotaDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(TasaCuota.class));
    }

    @Test
    public void addTasaCuota_notManagedExceptionIsThrown_customExceptionShouldBeThrown () throws Exception {
        when(repository.save(any(TasaCuota.class)))
            .thenThrow(new RuntimeException("Not managed exception"));
        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.addTasaCuota(tasaCuotaDTO);
        });
        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).save(any(TasaCuota.class));
    }

    @Test
    public void updateTasaCuota_notExistingEntity_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.FALSE);

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateTasaCuota(tasaCuotaDTO);
        });
        assertEquals(ErrorCode.NOT_FOUND, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());

        verify(repository).existsById(eq(id));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateTasaCuota_entityExistsInDB_successTransaction() throws Exception {
        when(repository.existsById(eq(id)))
           .thenReturn(Boolean.TRUE);

        TasaCuotaDTO dto = service.updateTasaCuota(tasaCuotaDTO);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());

        verify(repository).existsById(eq(id));

        verify(repository).save(any(TasaCuota.class));
    }

    @Test
    public void updateTasaCuota_errorFetchingFromDB_customExceptionShouldBeThrown() throws Exception {
        when(repository.existsById(eq(id)))
           .thenThrow(new RuntimeException("Not managed exception"));

        CatalogException cte = assertThrows(CatalogException.class, () -> {
            service.updateTasaCuota(tasaCuotaDTO);
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

        TasaCuotaDTO dto = service.findById(id);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());

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

        Collection<TasaCuotaDTO> dtoList = service.findAll();
        assertEquals(1, dtoList.size());
    }

    @Test
    public void getPaginatedResult_noParametersProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TasaCuotaDTO> dtoPage = service.getPaginatedResult(0, 10, null, null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());

        TasaCuotaDTO dto = dtoPage.getContent().get(0);
        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());
    }

    @Test
    public void getPaginatedResult_ascSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TasaCuotaDTO> dtoPage = service.getPaginatedResult(1, 10, "id", "asc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());
        TasaCuotaDTO dto = dtoPage.getContent().get(0);

        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());
    }

    @Test
    public void getPaginatedResult_descSortAndColumnProvided_pageIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TasaCuotaDTO> dtoPage = service.getPaginatedResult(0, 10, "id", "desc");

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());

        TasaCuotaDTO dto = dtoPage.getContent().get(0);
        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());
    }

    @Test
    public void getPaginatedResult_invalidParametersProvided_customExceptionIsThrown () throws Exception {        
        CatalogException cte = assertThrows(CatalogException.class, () -> {
            Page<TasaCuotaDTO> dtoPage = service.getPaginatedResult(0, 10, "invalidColumn", "invalidOrder");
        });
        

        assertEquals(ErrorCode.INTERNAL, cte.getCode());
        assertFalse(cte.getErrors().isEmpty());
        verifyNoInteractions(repository);
    }

    @Test
    public void getPaginatedResult_orderIsNull_defaultSortIsReturned () throws Exception {
        when(repository.findAll(any(PageRequest.class)))
            .thenReturn(new PageImpl(Collections.singletonList(entity), PageRequest.of(0, 10), 10));
        
        Page<TasaCuotaDTO> dtoPage = service.getPaginatedResult(0, 10, "is", null);

        assertNotNull(dtoPage);
        assertEquals(1, dtoPage.getContent().size());

        TasaCuotaDTO dto = dtoPage.getContent().get(0);
        assertEquals(id, dto.getId());
        assertEquals("Tasa", dto.getFactor());
        assertEquals("01/01/2021", dto.getFechaFin());
        assertEquals("01/01/2019", dto.getFechaInicio());
        assertEquals("IVA", dto.getImpuesto());
        assertEquals("Fijo", dto.getTipo());
        assertFalse(dto.getRetencion());
        assertTrue(dto.getTraslado());
        assertEquals(new BigDecimal(0.002), dto.getValorMaximo());
        assertEquals(new BigDecimal(0.001), dto.getValorMinimo());
    }

}
