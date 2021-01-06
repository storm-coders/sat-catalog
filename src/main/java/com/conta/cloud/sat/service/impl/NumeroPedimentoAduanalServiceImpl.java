package com.conta.cloud.sat.service.impl;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.conta.cloud.sat.service.NumeroPedimentoAduanalService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.NumeroPedimentoAduanalDTO;
import com.conta.cloud.sat.mappers.NumeroPedimentoAduanalMapper;
import com.conta.cloud.sat.persistence.NumeroPedimentoAduanalRepository;
import com.conta.cloud.sat.domain.NumeroPedimentoAduanal;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class NumeroPedimentoAduanalServiceImpl implements NumeroPedimentoAduanalService {

    private final Logger logger = LoggerFactory.getLogger(NumeroPedimentoAduanalServiceImpl.class);

    private final NumeroPedimentoAduanalRepository numeroPedimentoAduanalRepository;
    private final NumeroPedimentoAduanalMapper numeroPedimentoAduanalMapper;

    public NumeroPedimentoAduanalServiceImpl(NumeroPedimentoAduanalRepository numeroPedimentoAduanalRepository, //
        NumeroPedimentoAduanalMapper numeroPedimentoAduanalMapper) {
        this.numeroPedimentoAduanalRepository = numeroPedimentoAduanalRepository;
        this.numeroPedimentoAduanalMapper = numeroPedimentoAduanalMapper;
    }

    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("idAduana", "idAduana");
        columnNames.put("fechaInicio", "fechaInicio");
        columnNames.put("patente", "patente");
        columnNames.put("ejercicio", "ejercicio");
        columnNames.put("fechaFin", "fechaFin");
        columnNames.put("cantidad", "cantidad");
    }

    /**
    * Method to add new NumeroPedimentoAduanal in database
    * @param numeroPedimentoAduanalDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public NumeroPedimentoAduanalDTO addNumeroPedimentoAduanal(NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO) throws CatalogException{
        try{
            NumeroPedimentoAduanal numeroPedimentoAduanal = numeroPedimentoAduanalMapper.fromDTO(numeroPedimentoAduanalDTO);
            numeroPedimentoAduanalRepository.save(numeroPedimentoAduanal);
            return numeroPedimentoAduanalMapper.fromEntity(numeroPedimentoAduanal);
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
    * @param numeroPedimentoAduanalDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public NumeroPedimentoAduanalDTO updateNumeroPedimentoAduanal(NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO) throws CatalogException {
        try{
            if(!numeroPedimentoAduanalRepository.existsById(numeroPedimentoAduanalDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",numeroPedimentoAduanalDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            NumeroPedimentoAduanal numeroPedimentoAduanal = numeroPedimentoAduanalMapper.fromDTO(numeroPedimentoAduanalDTO);
            numeroPedimentoAduanalRepository.save(numeroPedimentoAduanal);
            return numeroPedimentoAduanalMapper.fromEntity(numeroPedimentoAduanal);
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
    * Get details of NumeroPedimentoAduanal
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public NumeroPedimentoAduanalDTO findById(String id) throws CatalogException{
        try{
            if(!numeroPedimentoAduanalRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return numeroPedimentoAduanalMapper.fromEntity(numeroPedimentoAduanalRepository.getOne(id));
        }catch(CatalogException excp){
            throw excp;
        }catch(Exception ex){
            logger.error("Not managed error in select transaction byId", ex);
            throw new CatalogException(ErrorCode.INTERNAL, //
               ErrorCode.INTERNAL.getCode().toString(), //
               INTERNAL_ERROR_MESSAGE);
        }
    }

    /**
    * Get List of all NumeroPedimentoAduanal
    * throws CatalogException if an error happens in select transaction
    * @param idAduana filter
    */
    public Collection<NumeroPedimentoAduanalDTO> findAll(String idAduana) throws CatalogException{
        try{
            if(StringUtils.isEmpty(idAduana)) {
                return numeroPedimentoAduanalRepository.findAll()
                    .stream()
                    .map(numeroPedimentoAduanalMapper::fromEntity)
                    .collect(Collectors.toList());
            } else {
                return numeroPedimentoAduanalRepository.findByIdAduana(idAduana)
                    .stream()
                    .map(numeroPedimentoAduanalMapper::fromEntity)
                    .collect(Collectors.toList());
            }
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
    public Page<NumeroPedimentoAduanalDTO> getPaginatedResult(
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
                Page<NumeroPedimentoAduanal> numeroPedimentoAduanalPage = numeroPedimentoAduanalRepository.findAll(request);
                List<NumeroPedimentoAduanalDTO> resourcesList = numeroPedimentoAduanalPage.getContent()
                                    .stream()
                                    .map(numeroPedimentoAduanalMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, numeroPedimentoAduanalPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
