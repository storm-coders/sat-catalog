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
import com.conta.cloud.sat.service.MonedaService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.MonedaDTO;
import com.conta.cloud.sat.mappers.MonedaMapper;
import com.conta.cloud.sat.persistence.MonedaRepository;
import com.conta.cloud.sat.domain.Moneda;
import org.springframework.dao.DataIntegrityViolationException;


@Service
@Transactional(readOnly = true)
public class MonedaServiceImpl implements MonedaService {

    private final Logger logger = LoggerFactory.getLogger(MonedaServiceImpl.class);

    private final MonedaRepository monedaRepository;
    private final MonedaMapper monedaMapper;

    public MonedaServiceImpl(MonedaRepository monedaRepository, //
        MonedaMapper monedaMapper) {
        this.monedaRepository = monedaRepository;
        this.monedaMapper = monedaMapper;
    }

    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("descripcion", "descripcion");
	    columnNames.put("noDecimales", "noDecimales");
	    columnNames.put("porcentajeVariacion", "porcentajeVariacion");
	    columnNames.put("fechaInicio", "fechaInicio");
	    columnNames.put("fechaFin", "fechaFin");
    }

    /**
    * Method to add new Moneda in database
    * @param monedaDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public MonedaDTO addMoneda(MonedaDTO monedaDTO) throws CatalogException{
        try{
            Moneda moneda = monedaMapper.fromDTO(monedaDTO);
            monedaRepository.save(moneda);
            return monedaMapper.fromEntity(moneda);
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
    * @param monedaDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public MonedaDTO updateMoneda(MonedaDTO monedaDTO) throws CatalogException {
        try{
            if(!monedaRepository.existsById(monedaDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",monedaDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            Moneda moneda = monedaMapper.fromDTO(monedaDTO);
            monedaRepository.save(moneda);
            return monedaMapper.fromEntity(moneda);
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
    * Get details of Moneda
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public MonedaDTO findById(String id) throws CatalogException{
        try{
            if(!monedaRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return monedaMapper.fromEntity(monedaRepository.getOne(id));
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
    * Get List of all Moneda
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<MonedaDTO> findAll() throws CatalogException{
        try{
            return monedaRepository.findAll()
            .stream()
            .map(monedaMapper::fromEntity)
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
    public Page<MonedaDTO> getPaginatedResult(
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
                Page<Moneda> monedaPage = monedaRepository.findAll(request);
                List<MonedaDTO> resourcesList = monedaPage.getContent()
                                    .stream()
                                    .map(monedaMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, monedaPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
