package com.conta.cloud.sat.service.impl;

import com.conta.cloud.sat.dto.ImpuestoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.mappers.ImpuestoMapper;
import com.conta.cloud.sat.persistence.ImpuestoRepository;
import com.conta.cloud.sat.service.ImpuestoService;
import com.conta.cloud.sat.service.ErrorCode;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ImpuestoServiceImpl implements ImpuestoService {

   private final ImpuestoMapper mapper;
   
   private final ImpuestoRepository repository;

   public ImpuestoServiceImpl(ImpuestoMapper mapper, //
		   ImpuestoRepository repository) {
      this.mapper = mapper;
      this.repository = repository;
   }

   public Collection<ImpuestoDTO> findAll() throws CatalogException {
      try{
	   return repository.findAll()
	      .stream()
	      .map(mapper::fromEntity)
	      .collect(Collectors.toList());
      } catch (Exception e) {
         log.error("Unable to read data from DB", e);
	 CatalogException ce = new CatalogException(ErrorCode.INTERNAL, //
			 Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, INTERNAL_ERROR_MESSAGE)));

	 throw ce;
      }
   }
}
