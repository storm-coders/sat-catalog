package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.EstadoDTO;
import com.conta.cloud.sat.exception.CatalogException;

public interface EstadoService {
	String INTERNAL_ERROR_MESSAGE = "Error al seleccionar catálogo de Estados";
	Collection<EstadoDTO> findAll() throws CatalogException;
	Collection<EstadoDTO> findByPais(String idPais) throws CatalogException;
}
