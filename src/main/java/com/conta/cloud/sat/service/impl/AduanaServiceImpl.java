package com.conta.cloud.sat.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.conta.cloud.sat.domain.Aduana;
import com.conta.cloud.sat.dto.AduanaDTO;
import com.conta.cloud.sat.mappers.AduanaMapper;
import com.conta.cloud.sat.persistence.AduanaRepository;
import com.conta.cloud.sat.service.AduanaService;
import com.conta.cloud.sat.service.ErrorCode;

import exception.CatalogException;
import exception.ErrorDetail;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AduanaServiceImpl implements AduanaService {
	
	private final AduanaMapper mapper;
	private final AduanaRepository repository;
	
	public AduanaServiceImpl(AduanaMapper mapper,
			AduanaRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}
	
	@Override
	public Collection<AduanaDTO> findAduanas(String description) throws CatalogException {
		try {
			Specification<Aduana> spec = Specification.where((root, query, cb) -> getPredicate(root, query, cb, description));
			return repository.findAll(spec)
					.stream()
					.map(mapper::fromEntity)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Unable to fetch Aduana from database - description: {} ", description, e);
			CatalogException ce = new CatalogException(ErrorCode.INTERNAL, //
					Collections.singletonList(new ErrorDetail("500", "Error al seleccionar cátalogo de Aduanas")));
			throw ce;
		}
	}

	public Predicate getPredicate(Root<Aduana> root, CriteriaQuery<?> query, CriteriaBuilder cb, String description) {
		Date today = new Date();
		Predicate predicate = cb.and(cb.lessThan(root.get("fechaInicio"), today) ,
		cb.or(cb.isNull(root.get("fechaFin")), cb.greaterThanOrEqualTo(root.get("fechaFin"), today)));
		if(!StringUtils.isEmpty(description)) {
			predicate = cb.and(cb.like(cb.lower(root.get("descripcion")), String.format("%s%s%s", "%", description.toLowerCase(), "%")));
		}
		return predicate;
	}
}
