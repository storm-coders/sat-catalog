package com.conta.cloud.sat.service.impl;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.conta.cloud.sat.service.TipoRelacionService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TipoRelacionDTO;
import com.conta.cloud.sat.mappers.TipoRelacionMapper;
import com.conta.cloud.sat.persistence.TipoRelacionRepository;
import com.conta.cloud.sat.domain.TipoRelacion;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class TipoRelacionServiceImpl implements TipoRelacionService {

    private final Logger logger = LoggerFactory.getLogger(TipoRelacionServiceImpl.class);

    private final TipoRelacionRepository tipoRelacionRepository;
    private final TipoRelacionMapper tipoRelacionMapper;

    public TipoRelacionServiceImpl(TipoRelacionRepository tipoRelacionRepository, //
        TipoRelacionMapper tipoRelacionMapper) {
        this.tipoRelacionRepository = tipoRelacionRepository;
        this.tipoRelacionMapper = tipoRelacionMapper;
    }
    
    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("descripcion", "descripcion");
        columnNames.put("fechaInicio", "fechaInicio");
        columnNames.put("fechaFin", "fechaFin");
    }

    /**
    * Method to add new TipoRelacion in database
    * @param tipoRelacionDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public TipoRelacionDTO addTipoRelacion(TipoRelacionDTO tipoRelacionDTO) throws CatalogException{
        try{
            TipoRelacion tipoRelacion = tipoRelacionMapper.fromDTO(tipoRelacionDTO);
            tipoRelacionRepository.save(tipoRelacion);
            return tipoRelacionMapper.fromEntity(tipoRelacion);
        }catch(DataIntegrityViolationException dve){
            logger.error("Error in insert transaction",dve);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }catch(Exception ex){
            logger.error("Not managed error in insert transaction",ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Method to update values of entity
    * @param tipoRelacionDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public TipoRelacionDTO updateTipoRelacion(TipoRelacionDTO tipoRelacionDTO) throws CatalogException {
        try{
            if(!tipoRelacionRepository.existsById(tipoRelacionDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",tipoRelacionDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            TipoRelacion tipoRelacion = tipoRelacionMapper.fromDTO(tipoRelacionDTO);
            tipoRelacionRepository.save(tipoRelacion);
            return tipoRelacionMapper.fromEntity(tipoRelacion);
        }catch(CatalogException xe){
            throw xe;
        }catch(Exception ex){
            logger.error("Not managed error in insert transaction",ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Get details of TipoRelacion
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public TipoRelacionDTO findById(String id) throws CatalogException{
        try{
            if(!tipoRelacionRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return tipoRelacionMapper.fromEntity(tipoRelacionRepository.getOne(id));
        }catch(CatalogException excp){
            throw excp;
        }catch(Exception ex){
            logger.error("Not managed error in select by id transaction", ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Get List of all TipoRelacion
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<TipoRelacionDTO> findAll() throws CatalogException{
        try{
            return tipoRelacionRepository.findAll()
            .stream()
            .map(tipoRelacionMapper::fromEntity)
            .collect(Collectors.toList());
        }catch(Exception ex){
            logger.error("Not managed error in select transaction", ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    public Page<TipoRelacionDTO> getPaginatedResult(
            Integer page,Integer size,String columnToOrder, String orderType
        ) throws CatalogException {
            try {
                Sort sort = Sort.by(Sort.Direction.ASC, (String) columnNames.get("id"));
                if (columnToOrder != null && orderType != null) {
                    if (orderType.equalsIgnoreCase("asc")) {
                        sort = Sort.by(Sort.Direction.ASC, (String) columnNames.get(columnToOrder));
                    }else {
                        sort = Sort.by(Sort.Direction.DESC, (String) columnNames.get(columnToOrder));
                    }
                }
                PageRequest request = PageRequest.of(page, size, sort);
                Page<TipoRelacion> tipoRelacionPage = tipoRelacionRepository.findAll(request);
                List<TipoRelacionDTO> resourcesList = tipoRelacionPage.getContent()
                                    .stream()
                                    .map(tipoRelacionMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, tipoRelacionPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
