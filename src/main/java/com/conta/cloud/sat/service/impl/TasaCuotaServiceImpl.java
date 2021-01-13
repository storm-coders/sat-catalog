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
import com.conta.cloud.sat.service.TasaCuotaService;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TasaCuotaDTO;
import com.conta.cloud.sat.mappers.TasaCuotaMapper;
import com.conta.cloud.sat.persistence.TasaCuotaRepository;
import com.conta.cloud.sat.domain.TasaCuota;
import org.springframework.dao.DataIntegrityViolationException;

/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
@Service
@Transactional(readOnly = true)
public class TasaCuotaServiceImpl implements TasaCuotaService {

    private final Logger logger = LoggerFactory.getLogger(TasaCuotaServiceImpl.class);

    private final TasaCuotaRepository tasaCuotaRepository;
    private final TasaCuotaMapper tasaCuotaMapper;

    public TasaCuotaServiceImpl(TasaCuotaRepository tasaCuotaRepository, //
        TasaCuotaMapper tasaCuotaMapper) {
        this.tasaCuotaRepository = tasaCuotaRepository;
        this.tasaCuotaMapper = tasaCuotaMapper;
    }

    private final Map<String,String> columnNames = new HashMap<>();

    {
        columnNames.put("id","id");
        columnNames.put("tipo", "tipo");
        columnNames.put("valorMinimo", "valorMinimo");
        columnNames.put("valorMaximo", "valorMaximo");
        columnNames.put("factor", "factor");
        columnNames.put("traslado", "traslado");
        columnNames.put("retencion", "retencion");
        columnNames.put("fechaInicio", "fechaInicio");
        columnNames.put("fechaFin", "fechaFin");
        columnNames.put("impuesto", "impuesto");
    }

    /**
    * Method to add new TasaCuota in database
    * @param tasaCuotaDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public TasaCuotaDTO addTasaCuota(TasaCuotaDTO tasaCuotaDTO) throws CatalogException{
        try{
            TasaCuota tasaCuota = tasaCuotaMapper.fromDTO(tasaCuotaDTO);
            tasaCuotaRepository.save(tasaCuota);
            return tasaCuotaMapper.fromEntity(tasaCuota);
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
    * @param tasaCuotaDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    @Transactional(rollbackFor=CatalogException.class)
    public TasaCuotaDTO updateTasaCuota(TasaCuotaDTO tasaCuotaDTO) throws CatalogException {
        try{
            if(!tasaCuotaRepository.existsById(tasaCuotaDTO.getId())){
                logger.error("Entity not exists in database - ID: {}",tasaCuotaDTO.getId());
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            TasaCuota tasaCuota = tasaCuotaMapper.fromDTO(tasaCuotaDTO);
            tasaCuotaRepository.save(tasaCuota);
            return tasaCuotaMapper.fromEntity(tasaCuota);
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
    * Get details of TasaCuota
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    public TasaCuotaDTO findById(String id) throws CatalogException{
        try{
            if(!tasaCuotaRepository.existsById(id)){
                logger.error("Entity not exists in database - ID: {}",id);
                throw new CatalogException(ErrorCode.NOT_FOUND, //
                   ErrorCode.NOT_FOUND.getCode().toString(), //
                   INTERNAL_ERROR_MESSAGE);
            }
            return tasaCuotaMapper.fromEntity(tasaCuotaRepository.getOne(id));
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
    * Get List of all TasaCuota
    * throws CatalogException if an error happens in select transaction
    */
    public Collection<TasaCuotaDTO> findAll() throws CatalogException{
        try{
            return tasaCuotaRepository.findAll()
            .stream()
            .map(tasaCuotaMapper::fromEntity)
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
    public Page<TasaCuotaDTO> getPaginatedResult(
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
                Page<TasaCuota> tasaCuotaPage = tasaCuotaRepository.findAll(request);
                List<TasaCuotaDTO> resourcesList = tasaCuotaPage.getContent()
                                    .stream()
                                    .map(tasaCuotaMapper::fromEntity)
                                    .collect(Collectors.toList());
                return new PageImpl<>(resourcesList, request, tasaCuotaPage.getTotalElements());
            }catch (Exception ex){
                logger.error("Not managed error in select transaction",ex);
                throw new CatalogException(ErrorCode.INTERNAL, //
                    ErrorCode.INTERNAL.getCode().toString(), //
                    INTERNAL_ERROR_MESSAGE);
            }
    }
}
