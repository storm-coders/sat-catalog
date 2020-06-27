package com.conta.cloud.sat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

import com.conta.cloud.sat.domain.Aduana;
import com.conta.cloud.sat.dto.AduanaDTO;
import com.conta.cloud.sat.mappers.AduanaMapper;
import com.conta.cloud.sat.persistence.AduanaRepository;
import com.conta.cloud.sat.service.impl.AduanaServiceImpl;

import exception.CatalogException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Aduana Service Testing")
public class AduanaServiceTest {

	private AduanaServiceImpl aduanaService;
	
	@Mock
	private AduanaRepository repository;
	private AduanaMapper mapper = Mappers.getMapper(AduanaMapper.class);
	
	@Mock
	private Root<Aduana> root;
	
	@Mock
	private CriteriaBuilder cb;
	
	@Mock
	private CriteriaQuery<?> query;
	
	@Mock
	private Predicate predicate;
	
	@BeforeEach
	public void setup() {
		aduanaService = new AduanaServiceImpl(mapper, repository);
	}
	
	@Test
	public void testExceptionFromDB() {
		when(repository.findAll(ArgumentMatchers.<Specification<Aduana>>any()))
		.thenThrow(new DataAccessException("Error on dabase call") {
			private static final long serialVersionUID = 1L;
		});
		CatalogException e = assertThrows(CatalogException.class, () -> {
			aduanaService.findAduanas("");
		});
		assertEquals(e.getCode(), ErrorCode.INTERNAL);
		assertEquals(e.getCode().getCode(), 500);
	}
	
	@Test
	public void testSuccessResponse()  throws Exception {
		when(repository.findAll(ArgumentMatchers.<Specification<Aduana>>any()))
		.thenReturn(Collections.singletonList(new Aduana("001", "Test", new Date(), null)));
		
		Collection<AduanaDTO> aduanas = aduanaService.findAduanas("Test");
		
		for (AduanaDTO aduanaDTO : aduanas) {
			assertEquals("001", aduanaDTO.getId());
			assertEquals("Test", aduanaDTO.getDescripcion());
			assertNotNull(aduanaDTO.getFechaInicio());
			assertNull(aduanaDTO.getFechaFin());
		}
	}
	
	@Test
	public void testPredicate_EmptyDescription() {
		Predicate predicate = aduanaService.getPredicate(root, query, cb, "");
		assertNull(predicate);
	}
	
	@Test
	public void testPredicate_NotEmptyDescription() {
		Predicate predicate = aduanaService.getPredicate(root, query, cb, "Test");
		assertNull(predicate);
	}
}
