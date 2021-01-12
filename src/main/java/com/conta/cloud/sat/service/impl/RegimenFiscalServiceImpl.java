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
import com.conta.cloud.sat.service.RegimenFiscalService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.RegimenFiscalDTO;
import com.conta.cloud.sat.mappers.RegimenFiscalMapper;
import com.conta.cloud.sat.persistence.RegimenFiscalRepository;
import com.conta.cloud.sat.domain.RegimenFiscal;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class RegimenFiscalServiceImpl implements RegimenFiscalService {

    private final Logger logger = LoggerFactory.getLogger(RegimenFiscalServiceImpl.class);

    private final RegimenFiscalRepository regimenFiscalRepository;
    private final RegimenFiscalMapper regimenFiscalMapper;

    public RegimenFiscalServiceImpl(RegimenFiscalRepository regimenFiscalRepository, //
        RegimenFiscalMapper regimenFiscalMapper) {
        this.regimenFiscalRepository = regimenFiscalRepository;
        this.regimenFiscalMapper = regimenFiscalMapper;
    }

    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("descripcion", "descripcion");
        columnNames.put("personaFisica", "personaFisica");
        columnNames.put("personaMoral", "personaMoral");
        columnNames.put("fechaInicio", "fechaInicio");
        columnNames.put("fechaFin", "fechaFin");
    }

    /**
    * Method to add new RegimenFiscal in database
    * @param regimenFiscalDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public RegimenFiscalDTO addRegimenFiscal(RegimenFiscalDTO regimenFiscalDTO) throws CatalogException{
        try{
            RegimenFiscal regimenFiscal = regimenFiscalMapper.fromDTO(regimenFiscalDTO);
            regimenFiscalRepository.save(regimenFiscal);
            return regimenFiscalMapper.fromEntity(regimenFiscal);
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
    * @param regimenFiscalDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public RegimenFiscalDTO updateRegimenFiscal(RegimenFiscalDTO regimenFiscalDTO) throws CatalogException {
        try{
            if(!regimenFiscalRepository.existsById(regimenFiscalDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",regimenFiscalDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            RegimenFiscal regimenFiscal = regimenFiscalMapper.fromDTO(regimenFiscalDTO);
            regimenFiscalRepository.save(regimenFiscal);
            return regimenFiscalMapper.fromEntity(regimenFiscal);
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
    * Get details of RegimenFiscal
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public RegimenFiscalDTO findById(String id) throws CatalogException{
        try{
            if(!regimenFiscalRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return regimenFiscalMapper.fromEntity(regimenFiscalRepository.getOne(id));
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
    * Get List of all RegimenFiscal
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<RegimenFiscalDTO> findAll() throws CatalogException{
        try{
            return regimenFiscalRepository.findAll()
            .stream()
            .map(regimenFiscalMapper::fromEntity)
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
    public Page<RegimenFiscalDTO> getPaginatedResult(
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
                Page<RegimenFiscal> regimenFiscalPage = regimenFiscalRepository.findAll(request);
                List<RegimenFiscalDTO> resourcesList = regimenFiscalPage.getContent()
                                    .stream()
                                    .map(regimenFiscalMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, regimenFiscalPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
