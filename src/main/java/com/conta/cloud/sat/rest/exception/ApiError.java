package com.conta.cloud.sat.rest.exception;

import java.util.List;

import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.service.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
	private ErrorCode code;
	private List<ErrorDetail> errors;
	
	public void addError(ErrorDetail detail) {
		errors.add(detail);
	}
}
