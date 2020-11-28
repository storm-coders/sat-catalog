package com.conta.cloud.sat.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.conta.cloud.sat.domain.Producto;
import com.conta.cloud.sat.dto.ProductoDTO;
import com.conta.cloud.sat.mappers.ProductoMapper;
import com.conta.cloud.sat.persistence.ProductoRepository;
import com.conta.cloud.sat.service.ProductoService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.exception.CatalogExceptionConstants;
import com.conta.cloud.sat.exception.ErrorDetail;

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
public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoMapper mapper;
    private final ProductoRepository repository;
    private final String ID = "idProductoServicio";
    private final String DESC = "descripcion";
    private final String END_DT = "fechaFin";
    private final String START_DT = "fechaInicio";

    public ProductoServiceImpl(ProductoMapper mapper, //
        ProductoRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Collection<ProductoDTO> findProductos(String id, String descripcion) //
        throws CatalogException {
        try {
            List<Producto> productos = null;
            if(!hasFilter(id, descripcion))  {
                productos = repository//
			.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, ID)))//
			.getContent();
            } else {
                Specification<Producto> productoSpec = getSpecification(id, descripcion);
                productos = repository.findAll(productoSpec);
            }
            return productos
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Unable to fetch Productos", ex);
                throw new CatalogException(ErrorCode.INTERNAL,
                            Collections.singletonList(new ErrorDetail(CatalogExceptionConstants.INTERNAL_CODE, //
                            INTERNAL_ERROR_MESSAGE))); 
        }    
    }
    
    private Boolean hasFilter(String id, String descripcion) {
        return !(StringUtils.isEmpty(id) && StringUtils.isEmpty(descripcion));
    }

    private Specification<Producto> getSpecification(String id, String descripcion) {
        return Specification.where((root, query, cb) -> getPredicate(root, query, cb, id, descripcion));
    }

    @Generated
    private Predicate getPredicate(Root<Producto> root, //
        CriteriaQuery<?> query, CriteriaBuilder cb, String id, String descripcion) {
            Predicate p = null;
            if(!StringUtils.isEmpty(id)) {
                p = cb.like(root.get(ID), String.format("%s%s", "%", id ));
            }

            if(!StringUtils.isEmpty(descripcion)) {
                Predicate descPredicate = cb.like(cb.lower(root.get(DESC)), //
                     String.format("%s%s%s", "%", descripcion.toLowerCase(), "%"));
                p = p == null ? descPredicate : cb.and(p, descPredicate);     
            }

            Date btDate = new Date();

            Predicate starDate = cb.or( //
                cb.isNull(root.get(START_DT)), //
                cb.lessThanOrEqualTo(root.get(START_DT), btDate)
                );

            Predicate endDate = cb.or(
                cb.isNull(root.get(END_DT)), //
                cb.lessThanOrEqualTo(root.get(END_DT), btDate)
                );
            p = p == null ? cb.and(starDate, endDate) : cb.and(p, starDate, endDate) ;
            return p;
    }

}
