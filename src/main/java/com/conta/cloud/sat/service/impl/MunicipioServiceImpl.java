package com.conta.cloud.sat.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.conta.cloud.sat.domain.Municipio;
import com.conta.cloud.sat.dto.MunicipioDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.mappers.MunicipioMapper;
import com.conta.cloud.sat.persistence.MunicipiosRepository;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.service.MunicipiosService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class MunicipioServiceImpl implements MunicipiosService {

	private final MunicipiosRepository repository;
	private final MunicipioMapper mapper;
	
	public MunicipioServiceImpl(MunicipiosRepository repository,
			MunicipioMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Collection<MunicipioDTO> findMunicipios(String id) throws CatalogException {
		try {
			Collection<Municipio> municipio;
			if(StringUtils.isEmpty(id)) {
				municipio = repository.findAll();
			} else {
				municipio = repository.findByIdEstado(id);
			}
			return municipio.stream()
					.map(mapper::fromEntity)
					.collect(Collectors.toList());
		}catch (Exception e) {
			log.error("Unable to fetch Municipio's from DB - idEstado {}", id, e);
			throw new CatalogException(ErrorCode.INTERNAL,
					Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, INTERNAL_ERROR_MESSAGE)));
		}
	}
	
	
}
