package com.conta.cloud.sat.rest.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.service.ErrorCode;

@Primary
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private final Map<String, String> errorCodes = new HashMap<>();
	
	{
		errorCodes.put(ValidationMessageConstants.INVALID_CP, "001");
		errorCodes.put(ValidationMessageConstants.INVALID_ID_ESTADO, "002");
		errorCodes.put(ValidationMessageConstants.INVALID_ID_MUNICIPIO, "003");
	}
	
	@ExceptionHandler(CatalogException.class)
	public ResponseEntity<ApiError> handleCatalogException(CatalogException exception) {
		ApiError apiError = new ApiError(exception.getCode(), exception.getErrors());
		HttpStatus status = HttpStatus.valueOf(exception.getCode().getCode());
		return new ResponseEntity<>(apiError, status);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstriaingViolation(ConstraintViolationException cve) {
		ApiError apiError = new ApiError(ErrorCode.BAD_REQUEST, new ArrayList());
		Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
		for(ConstraintViolation cv : violations) {
			String message = cv.getMessage();
			String code = errorCodes.get(message);
			if(code != null) {
				apiError.addError(new ErrorDetail(code, message));
			}
		}
		Collections.sort(apiError.getErrors(), (e1, e2) -> e1.getCode().compareTo(e2.getCode()));
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}
}
