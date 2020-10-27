package com.conta.cloud.sat.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.conta.cloud.sat.mappers.UnidadMapper;
import com.conta.cloud.sat.persistence.UnidadRepository;
import com.conta.cloud.sat.domain.Unidad;
import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;
import com.conta.cloud.sat.service.UnidadService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.util.DateUtils;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UnidadServiceImpl implements UnidadService {

    private final UnidadMapper mapper;
    private final UnidadRepository repository;

    public UnidadServiceImpl(UnidadMapper mapper, //
        UnidadRepository repository
    ) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Collection<UnidadDTO> findUnidades(
        String nombre,
        String simbolo,
        String fechaInicio
        )
        throws CatalogException {
            try {
                List<Unidad> unidades;
                if(!hasFilter(nombre, simbolo, fechaInicio)) {
                    unidades =  repository.findAll();
                } else {
                    Date fechaInicioDate = DateUtils.fromString(fechaInicio);
                    if(!StringUtils.isEmpty(fechaInicio) && fechaInicioDate == null) {
                        throw new CatalogException(ErrorCode.BAD_REQUEST,
                            Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.BAD_REQUEST_CODE, //
                            BAD_DATE_FORMAT))); 
                    }
                    Specification<Unidad> unidadSpec = getSpecification(nombre, simbolo, fechaInicioDate);
                    unidades = repository.findAll(unidadSpec);
                }
                return unidades
                    .stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());
            } catch (CatalogException cte) {
                throw cte;
            } catch (Exception ex) {
                log.error("Unable to fetch Unidades", ex);
                throw new CatalogException(ErrorCode.INTERNAL,
                            Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, //
                            INTERNAL_ERROR_MESSAGE))); 
            }

    }

    private boolean hasFilter(String nombre, String simbolo, String fechaInicio) {
        return !(StringUtils.isEmpty(nombre)
          && StringUtils.isEmpty(simbolo)
          && StringUtils.isEmpty(fechaInicio));
    }

    private Specification<Unidad> getSpecification(String nombre, String simbolo, Date fechaInicio) {
            return Specification.where((root, query, cb) -> getPredicate(root, query, cb, nombre, simbolo, fechaInicio));
    }

    @Generated
    private Predicate getPredicate(Root<Unidad> root, //
    CriteriaQuery<?> query, CriteriaBuilder cb, String nombre, String simbolo, Date fechaInicio) {
        Predicate p = null;  
        if(!StringUtils.isEmpty(nombre)) {
            p = cb.like(cb.lower(root.get("nombre")), String.format("%s%s%s","%", nombre.toLowerCase(), "%"));
        }
        if(!StringUtils.isEmpty(simbolo)) {
            Predicate like = cb.like(cb.lower(root.get("simbolo")), String.format("%s%s", "%", simbolo.toLowerCase()));
            p = p == null ? like : cb.and(p, like);
        }
        if(fechaInicio != null) {
            Predicate gte = cb.greaterThanOrEqualTo(root.get("fechaInicio"), fechaInicio);
            p = p == null ? gte : cb.and(p, gte);            
        }     
        Predicate btDates = cb.or(
                                cb.isNull(root.get("fechaFin")), //
                                cb.lessThanOrEqualTo(root.get("fechaFin"), new Date())
                                );
        p = p == null ? btDates : cb.and(p, btDates);
        return p;
    }   

}