package com.conta.cloud.sat.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.conta.cloud.sat.domain.CodigoPostal;
import com.conta.cloud.sat.dto.CodigoPostalDTO;
import com.conta.cloud.sat.mappers.CodigoPostalMapper;
import com.conta.cloud.sat.persistence.CodigoPostalRepository;
import com.conta.cloud.sat.service.CodigoPostalService;
import com.conta.cloud.sat.service.ErrorCode;
import exception.CatalogException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CodigoPostalServiceImpl implements CodigoPostalService {
	
	private final CodigoPostalRepository codigoPostalRepository;
	private final CodigoPostalMapper codigoPostalMapper;
	
	public CodigoPostalServiceImpl(CodigoPostalRepository codigoPostalRepository, //
			CodigoPostalMapper codigoPostalMapper) {
		this.codigoPostalRepository = codigoPostalRepository;
		this.codigoPostalMapper = codigoPostalMapper;
	}
	
	public Collection<CodigoPostalDTO> findCodigoPostal(String idEstado, String idMunicipio, String codigoPostal) 
		throws CatalogException{				
		try {
			Specification<CodigoPostal> cpSpecification = getSpecification(idEstado, idMunicipio, codigoPostal);
			return codigoPostalRepository.findAll(cpSpecification)
					.stream()
					.map(codigoPostalMapper::fromEntity)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Unable to select CodigoPostal from database - idEstado: {} - idMunicipio: {} - cp: {}",
					idEstado, idMunicipio, codigoPostal, e);
			CatalogException exception = new CatalogException(ErrorCode.INTERNAL);
			throw exception;
		}
	}
	
	private Specification<CodigoPostal> getSpecification(String idEstado, String idMunicipio, String codigoPostal) 
		throws CatalogException{
		return Specification.where((root, query, cb) -> getPredicate(root, query,cb, idEstado, idMunicipio, codigoPostal));
	}
	
	@Generated
	private Predicate getPredicate(Root<CodigoPostal> root,
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder,
			String idEstado, String idMunicipio, String codigoPostal) {
		Predicate predicate = null;
		if(!StringUtils.isEmpty(idEstado)) {
			predicate = criteriaBuilder.equal(root.get("id").get("idEstado"), idEstado);
		}
		if(!StringUtils.isEmpty(idMunicipio)) {
			Predicate equal = criteriaBuilder.equal(root.get("codigoMunicipio"), idMunicipio);
			predicate = predicate == null ? equal : criteriaBuilder.and(predicate, equal);
		}
		if(!StringUtils.isEmpty(codigoPostal)) {
			Predicate like = criteriaBuilder.like(root.get("id").get("idCodigoPostal"),
					String.format("%s%s", codigoPostal,"%"));
			predicate = predicate == null ? like : criteriaBuilder.and(predicate, like);
		}
		return predicate;
	}
}
