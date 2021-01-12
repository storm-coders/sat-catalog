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
import com.conta.cloud.sat.service.PatenteAduanalService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.PatenteAduanalDTO;
import com.conta.cloud.sat.mappers.PatenteAduanalMapper;
import com.conta.cloud.sat.persistence.PatenteAduanalRepository;
import com.conta.cloud.sat.domain.PatenteAduanal;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class PatenteAduanalServiceImpl implements PatenteAduanalService {

    private final Logger logger = LoggerFactory.getLogger(PatenteAduanalServiceImpl.class);

    private final PatenteAduanalRepository patenteAduanalRepository;
    private final PatenteAduanalMapper patenteAduanalMapper;

    public PatenteAduanalServiceImpl(PatenteAduanalRepository patenteAduanalRepository, //
        PatenteAduanalMapper patenteAduanalMapper) {
        this.patenteAduanalRepository = patenteAduanalRepository;
        this.patenteAduanalMapper = patenteAduanalMapper;
    }

    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("fechaInicio", "fechaInicio");
        columnNames.put("fechaFin", "fechaFin");
    }

    /**
    * Method to add new PatenteAduanal in database
    * @param patenteAduanalDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public PatenteAduanalDTO addPatenteAduanal(PatenteAduanalDTO patenteAduanalDTO) throws CatalogException{
        try{
            PatenteAduanal patenteAduanal = patenteAduanalMapper.fromDTO(patenteAduanalDTO);
            patenteAduanalRepository.save(patenteAduanal);
            return patenteAduanalMapper.fromEntity(patenteAduanal);
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
    * @param patenteAduanalDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public PatenteAduanalDTO updatePatenteAduanal(PatenteAduanalDTO patenteAduanalDTO) throws CatalogException {
        try{
            if(!patenteAduanalRepository.existsById(patenteAduanalDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",patenteAduanalDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            PatenteAduanal patenteAduanal = patenteAduanalMapper.fromDTO(patenteAduanalDTO);
            patenteAduanalRepository.save(patenteAduanal);
            return patenteAduanalMapper.fromEntity(patenteAduanal);
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
    * Get details of PatenteAduanal
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public PatenteAduanalDTO findById(String id) throws CatalogException{
        try{
            if(!patenteAduanalRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return patenteAduanalMapper.fromEntity(patenteAduanalRepository.getOne(id));
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
    * Get List of all PatenteAduanal
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<PatenteAduanalDTO> findAll() throws CatalogException{
        try{
            return patenteAduanalRepository.findAll()
            .stream()
            .map(patenteAduanalMapper::fromEntity)
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
    public Page<PatenteAduanalDTO> getPaginatedResult(
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
                Page<PatenteAduanal> patenteAduanalPage = patenteAduanalRepository.findAll(request);
                List<PatenteAduanalDTO> resourcesList = patenteAduanalPage.getContent()
                                    .stream()
                                    .map(patenteAduanalMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, patenteAduanalPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
