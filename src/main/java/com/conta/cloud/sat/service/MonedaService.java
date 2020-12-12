package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.MonedaDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface MonedaService{

    // TODO add specific message
    String INTERNAL_ERROR_MESSAGE = "";

    /**
    * Method to add new Moneda in database
    * @param monedaDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    MonedaDTO addMoneda(MonedaDTO monedaDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param monedaDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    MonedaDTO updateMoneda(MonedaDTO monedaDTO) throws CatalogException ;

    /**
    * Get details of Moneda
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    MonedaDTO findById(String id) throws CatalogException;

    /**
    * Get List of all Moneda
    * throws CatalogException if an error happens in select transaction
    */
    Collection<MonedaDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<MonedaDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
