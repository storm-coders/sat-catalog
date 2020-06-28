package com.conta.cloud.sat.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conta.cloud.sat.dto.EstadoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.mappers.EstadoMapper;
import com.conta.cloud.sat.persistence.EstadoRepository;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.service.EstadoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class EstadoServiceImpl implements EstadoService {

	private final EstadoRepository repository;
	private final EstadoMapper mapper;
	
	public EstadoServiceImpl(EstadoRepository repository,
			EstadoMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public Collection<EstadoDTO> findAll() throws CatalogException {
		try {
			return repository
					.findAll()
					.parallelStream()
					.map(mapper::fromEntity)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Unable to fetch Estado's from DB", e);
			throw new CatalogException(ErrorCode.INTERNAL, 
					Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, INTERNAL_ERROR_MESSAGE))
					);
		}
	}

	@Override
	public Collection<EstadoDTO> findByPais(String idPais) throws CatalogException {
		try {
			return repository
					.findByIdPais(idPais)
					.parallelStream()
					.map(mapper::fromEntity)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Unable to fetch Estado's from DB - idPais: {}", idPais, e);
			throw new CatalogException(ErrorCode.INTERNAL, 
					Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, INTERNAL_ERROR_MESSAGE))
					);
		}
	}

}
