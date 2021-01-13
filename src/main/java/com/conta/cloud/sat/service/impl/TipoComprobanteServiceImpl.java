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
import com.conta.cloud.sat.service.TipoComprobanteService;
import com.conta.cloud.sat.service.ErrorCode; // TODO make this dinamyc
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TipoComprobanteDTO;
import com.conta.cloud.sat.mappers.TipoComprobanteMapper;
import com.conta.cloud.sat.persistence.TipoComprobanteRepository;
import com.conta.cloud.sat.domain.TipoComprobante;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class TipoComprobanteServiceImpl implements TipoComprobanteService {

    private final Logger logger = LoggerFactory.getLogger(TipoComprobanteServiceImpl.class);

    private final TipoComprobanteRepository tipoComprobanteRepository;
    private final TipoComprobanteMapper tipoComprobanteMapper;

    public TipoComprobanteServiceImpl(TipoComprobanteRepository tipoComprobanteRepository, //
        TipoComprobanteMapper tipoComprobanteMapper) {
        this.tipoComprobanteRepository = tipoComprobanteRepository;
        this.tipoComprobanteMapper = tipoComprobanteMapper;
    }

    //TODO add attributes to order paginated result
    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
    }

    /**
    * Method to add new TipoComprobante in database
    * @param tipoComprobanteDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public TipoComprobanteDTO addTipoComprobante(TipoComprobanteDTO tipoComprobanteDTO) throws CatalogException{
        try{
            TipoComprobante tipoComprobante = tipoComprobanteMapper.fromDTO(tipoComprobanteDTO);
            tipoComprobanteRepository.save(tipoComprobante);
            return tipoComprobanteMapper.fromEntity(tipoComprobante);
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
    * @param tipoComprobanteDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public TipoComprobanteDTO updateTipoComprobante(TipoComprobanteDTO tipoComprobanteDTO) throws CatalogException {
        try{
            if(!tipoComprobanteRepository.existsById(tipoComprobanteDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",tipoComprobanteDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            TipoComprobante tipoComprobante = tipoComprobanteMapper.fromDTO(tipoComprobanteDTO);
            tipoComprobanteRepository.save(tipoComprobante);
            return tipoComprobanteMapper.fromEntity(tipoComprobante);
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
    * Get details of TipoComprobante
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public TipoComprobanteDTO findById(String id) throws CatalogException{
        try{
            if(!tipoComprobanteRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return tipoComprobanteMapper.fromEntity(tipoComprobanteRepository.getOne(id));
        }catch(CatalogException excp){
            throw excp;
        }catch(Exception ex){
            logger.error("Not managed error in select transaction", ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Get List of all TipoComprobante
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<TipoComprobanteDTO> findAll() throws CatalogException{
        try{
            return tipoComprobanteRepository.findAll()
            .stream()
            .map(tipoComprobanteMapper::fromEntity)
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
    public Page<TipoComprobanteDTO> getPaginatedResult(
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
                Page<TipoComprobante> tipoComprobantePage = tipoComprobanteRepository.findAll(request);
                List<TipoComprobanteDTO> resourcesList = tipoComprobantePage.getContent()
                                    .stream()
                                    .map(tipoComprobanteMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, tipoComprobantePage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
