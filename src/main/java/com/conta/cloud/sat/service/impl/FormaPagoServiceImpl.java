package com.conta.cloud.sat.service.impl;

import com.conta.cloud.sat.domain.FormaPago;
import com.conta.cloud.sat.dto.FormaPagoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.mappers.FormaPagoMapper;
import com.conta.cloud.sat.persistence.FormaPagoRepository;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.service.FormaPagoService;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class FormaPagoServiceImpl implements FormaPagoService {
   private final FormaPagoMapper mapper;
   private final FormaPagoRepository repository;

   public FormaPagoServiceImpl(FormaPagoMapper mapper,//
		   FormaPagoRepository repository) {
      this.mapper = mapper;
      this.repository = repository;      
   }

   @Override
   public Collection<FormaPagoDTO> findAll() throws CatalogException {
      try {
         Collection<FormaPago> formasPago = repository.findAll();
	 return formasPago
		 .stream()
		 .map(mapper::fromEntity)
		 .collect(Collectors.toList());
      }catch(Exception e) {
         log.error("unable to read from repository", e);
         throw new CatalogException(ErrorCode.INTERNAL,
			 Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, INTERNAL_ERROR_MESSAGE)));	 
      }
   }
}
