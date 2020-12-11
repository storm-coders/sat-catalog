package com.conta.cloud.sat.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INTERNAL(500, "Error al procesar petición"),
	BAD_REQUEST(400, "Parametros no validos"),
	NOT_FOUND(404, "No se encontro objeto");

	private final Integer code;
	private final String message;
}
