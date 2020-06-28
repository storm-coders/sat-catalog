package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.CodigoPostalDTO;
import com.conta.cloud.sat.exception.CatalogException;

public interface CodigoPostalService {
	String INTERNAL_ERROR_MESSAGE = "Error al seleccionar código postal";
	Collection<CodigoPostalDTO> findCodigoPostal(String idEstado, //
			String idMunicipio, String codigoPostal) throws CatalogException;
}
