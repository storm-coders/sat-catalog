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
import com.conta.cloud.sat.service.MetodoPagoService;
import com.conta.cloud.sat.service.ErrorCode; 
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.MetodoPagoDTO;
import com.conta.cloud.sat.mappers.MetodoPagoMapper;
import com.conta.cloud.sat.persistence.MetodoPagoRepository;
import com.conta.cloud.sat.domain.MetodoPago;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class MetodoPagoServiceImpl implements MetodoPagoService {

    private final Logger logger = LoggerFactory.getLogger(MetodoPagoServiceImpl.class);

    private final MetodoPagoRepository metodoPagoRepository;
    private final MetodoPagoMapper metodoPagoMapper;

    private final String ERROR_LOG = "Not managed error in select transaction";

    public MetodoPagoServiceImpl(MetodoPagoRepository metodoPagoRepository, //
        MetodoPagoMapper metodoPagoMapper) {
        this.metodoPagoRepository = metodoPagoRepository;
        this.metodoPagoMapper = metodoPagoMapper;
    }

    
    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","idMetodoPago");
        columnNames.put("descripcion","descripcion");
        columnNames.put("fechaInicio","fechaInicio");
        columnNames.put("fechaFin","fechaFin");
    }

    /**
    * Method to add new MetodoPago in database
    * @param metodoPagoDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public MetodoPagoDTO addMetodoPago(MetodoPagoDTO metodoPagoDTO) throws CatalogException{
        try{
            MetodoPago metodoPago = metodoPagoMapper.fromDTO(metodoPagoDTO);
            metodoPagoRepository.save(metodoPago);
            return metodoPagoMapper.fromEntity(metodoPago);
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
    * @param metodoPagoDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public MetodoPagoDTO updateMetodoPago(MetodoPagoDTO metodoPagoDTO) throws CatalogException {
        try{
            if(!metodoPagoRepository.existsById(metodoPagoDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",metodoPagoDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            MetodoPago metodoPago = metodoPagoMapper.fromDTO(metodoPagoDTO);
            metodoPagoRepository.save(metodoPago);
            return metodoPagoMapper.fromEntity(metodoPago);
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
    * Get details of MetodoPago
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public MetodoPagoDTO findById(String id) throws CatalogException{
        try{
            if(!metodoPagoRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return metodoPagoMapper.fromEntity(metodoPagoRepository.getOne(id));
        }catch(CatalogException excp){
            throw excp;
        }catch(Exception ex){
            logger.error(ERROR_LOG, ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Get List of all MetodoPago
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<MetodoPagoDTO> findAll() throws CatalogException{
        try{
            return metodoPagoRepository.findAll()
            .stream()
            .map(metodoPagoMapper::fromEntity)
            .collect(Collectors.toList());
        }catch(Exception ex){
            logger.error(ERROR_LOG, ex);
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
    public Page<MetodoPagoDTO> getPaginatedResult(
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
                Page<MetodoPago> metodoPagoPage = metodoPagoRepository.findAll(request);
                List<MetodoPagoDTO> resourcesList = metodoPagoPage.getContent()
                                    .stream()
                                    .map(metodoPagoMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, metodoPagoPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
