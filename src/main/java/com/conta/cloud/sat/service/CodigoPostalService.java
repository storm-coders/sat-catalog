package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.CodigoPostalDTO;

import exception.CatalogException;

public interface CodigoPostalService {
	Collection<CodigoPostalDTO> findCodigoPostal(String idEstado, //
			String idMunicipio, String codigoPostal) throws CatalogException;
}
