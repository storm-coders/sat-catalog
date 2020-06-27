package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.AduanaDTO;

import exception.CatalogException;

public interface AduanaService {
	Collection<AduanaDTO> findAduanas(String description) throws CatalogException;
}
