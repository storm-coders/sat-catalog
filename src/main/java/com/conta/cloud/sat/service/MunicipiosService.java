package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.MunicipioDTO;
import com.conta.cloud.sat.exception.CatalogException;


public interface MunicipiosService {
	String INTERNAL_ERROR_MESSAGE = "Error al seleccionar catálogo de Municipios";
	Collection<MunicipioDTO> findMunicipios(String id) throws CatalogException;
}
