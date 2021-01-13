package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TasaCuotaDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface TasaCuotaService{

    String INTERNAL_ERROR_MESSAGE = "Error al procesar Tasa/Cuota";

    /**
    * Method to add new TasaCuota in database
    * @param tasaCuotaDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    TasaCuotaDTO addTasaCuota(TasaCuotaDTO tasaCuotaDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param tasaCuotaDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    TasaCuotaDTO updateTasaCuota(TasaCuotaDTO tasaCuotaDTO) throws CatalogException ;

    /**
    * Get details of TasaCuota
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    TasaCuotaDTO findById(String id) throws CatalogException;

    /**
    * Get List of all TasaCuota
    * throws CatalogException if an error happens in select transaction
    */
    Collection<TasaCuotaDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<TasaCuotaDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
