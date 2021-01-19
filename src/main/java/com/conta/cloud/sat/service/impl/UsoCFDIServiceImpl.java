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
import com.conta.cloud.sat.service.UsoCFDIService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.UsoCFDIDTO;
import com.conta.cloud.sat.mappers.UsoCFDIMapper;
import com.conta.cloud.sat.persistence.UsoCFDIRepository;
import com.conta.cloud.sat.domain.UsoCFDI;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class UsoCFDIServiceImpl implements UsoCFDIService {

    private final Logger logger = LoggerFactory.getLogger(UsoCFDIServiceImpl.class);

    private final UsoCFDIRepository usoCFDIRepository;
    private final UsoCFDIMapper usoCFDIMapper;

    public UsoCFDIServiceImpl(UsoCFDIRepository usoCFDIRepository, //
        UsoCFDIMapper usoCFDIMapper) {
        this.usoCFDIRepository = usoCFDIRepository;
        this.usoCFDIMapper = usoCFDIMapper;
    }

    
    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("descripcion", "descripcion");
        columnNames.put("fechaInicio", "fechaInicio");
        columnNames.put("fechaFin", "fechaFin");
    }

    /**
    * Method to add new UsoCFDI in database
    * @param usoCFDIDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public UsoCFDIDTO addUsoCFDI(UsoCFDIDTO usoCFDIDTO) throws CatalogException{
        try{
            UsoCFDI usoCFDI = usoCFDIMapper.fromDTO(usoCFDIDTO);
            usoCFDIRepository.save(usoCFDI);
            return usoCFDIMapper.fromEntity(usoCFDI);
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
    * @param usoCFDIDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public UsoCFDIDTO updateUsoCFDI(UsoCFDIDTO usoCFDIDTO) throws CatalogException {
        try{
            if(!usoCFDIRepository.existsById(usoCFDIDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",usoCFDIDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            UsoCFDI usoCFDI = usoCFDIMapper.fromDTO(usoCFDIDTO);
            usoCFDIRepository.save(usoCFDI);
            return usoCFDIMapper.fromEntity(usoCFDI);
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
    * Get details of UsoCFDI
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public UsoCFDIDTO findById(String id) throws CatalogException{
        try{
            if(!usoCFDIRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return usoCFDIMapper.fromEntity(usoCFDIRepository.getOne(id));
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
    * Get List of all UsoCFDI
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<UsoCFDIDTO> findAll() throws CatalogException{
        try{
            return usoCFDIRepository.findAll()
            .stream()
            .map(usoCFDIMapper::fromEntity)
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
    public Page<UsoCFDIDTO> getPaginatedResult(
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
                Page<UsoCFDI> usoCFDIPage = usoCFDIRepository.findAll(request);
                List<UsoCFDIDTO> resourcesList = usoCFDIPage.getContent()
                                    .stream()
                                    .map(usoCFDIMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, usoCFDIPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
