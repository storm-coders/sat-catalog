package com.conta.cloud.sat.rest;

import java.util.Set;

import com.conta.cloud.sat.service.ErrorCode;

import exception.ErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorWrapperResponse {
	private ErrorCode code;
	private Set<ErrorDetail> errors;
}
