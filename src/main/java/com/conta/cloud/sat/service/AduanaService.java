package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.AduanaDTO;
import com.conta.cloud.sat.exception.CatalogException;

public interface AduanaService {
	String INTERNAL_ERROR_MESSAGE = "Error al seleccionar cátalogo de Aduanas";
	Collection<AduanaDTO> findAduanas(String description) throws CatalogException;
}
