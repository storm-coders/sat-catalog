package com.conta.cloud.sat.rest.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.conta.cloud.sat.service.ErrorCode;

import exception.CatalogException;
import exception.ErrorDetail;

@ExtendWith(MockitoExtension.class)
@DisplayName("Exception Handler Testing")
public class ApiExceptionHandlerTest {
	
	private ApiExceptionHandler exceptionHandler;
	
	@Mock
	private ConstraintViolation<?> knownViolation;
	
	@Mock
	private ConstraintViolation<?> unknownViolation;
	
	@BeforeEach
	public void setup() {
		exceptionHandler = new ApiExceptionHandler();
	}
	
	@Test
	public void handleCatalogException() {
		CatalogException catalogException = new CatalogException(ErrorCode.BAD_REQUEST, Collections.emptyList());
		ResponseEntity<ApiError> error = exceptionHandler.handleCatalogException(catalogException);
		
		assertNotNull(error);
		assertEquals(HttpStatus.valueOf(ErrorCode.BAD_REQUEST.getCode()), error.getStatusCode());
		assertNotNull(error.getBody());
		assertEquals(ErrorCode.BAD_REQUEST, error.getBody().getCode());
		
	}
	
	@Test
	public void handleConstraintViolation() {
		Set<ConstraintViolation<?>> constraintViolations = new HashSet<ConstraintViolation<?>>();
		constraintViolations.add(knownViolation);
		constraintViolations.add(unknownViolation);
		
		when(knownViolation.getMessage())
		.thenReturn(ValidationMessageConstants.INVALID_CP);
		
		when(unknownViolation.getMessage())
		.thenReturn("Invalid parameter");
		
		ConstraintViolationException cve = new ConstraintViolationException(constraintViolations);
		
		ResponseEntity<ApiError> apiErrorResponse = exceptionHandler.handleConstriaingViolation(cve);
		
		assertNotNull(apiErrorResponse);
		assertEquals(HttpStatus.valueOf(ErrorCode.BAD_REQUEST.getCode()), apiErrorResponse.getStatusCode());
		assertNotNull(apiErrorResponse.getBody());
		assertEquals(ErrorCode.BAD_REQUEST, apiErrorResponse.getBody().getCode());
		assertFalse(apiErrorResponse.getBody().getErrors().isEmpty());
		
		ErrorDetail detail = apiErrorResponse.getBody().getErrors().get(0);
		
		assertEquals("001", detail.getCode());
		assertEquals(ValidationMessageConstants.INVALID_CP, detail.getReason());
		
	}

}
