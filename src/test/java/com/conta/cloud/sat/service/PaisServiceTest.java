package com.conta.cloud.sat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.conta.cloud.sat.dto.PaisDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.service.impl.PaisServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pais Service Testing")
public class PaisServiceTest {
    

    @Test
    public void findPaises() throws Exception {
        PaisService service = new PaisServiceImpl(new ObjectMapper());
        Collection<PaisDTO> paises = service.findPaises();
        assertFalse(paises.isEmpty());
    }

    @Test
    public void findPaises_shouldThrowException() throws Exception {
        PaisService service = new PaisServiceImpl(null);    

        CatalogException cta = assertThrows(CatalogException.class, () -> service.findPaises());
		
		assertEquals(ErrorCode.INTERNAL, cta.getCode());
		assertFalse(cta.getErrors().isEmpty());
		assertEquals(CatalogExceptionConstants.INTERNAL_CODE, cta.getErrors().get(0).getCode());
		assertEquals(PaisService.INTERNAL_ERROR_MESSAGE, cta.getErrors().get(0).getReason());
    }
}