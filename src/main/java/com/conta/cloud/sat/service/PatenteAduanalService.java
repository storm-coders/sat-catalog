package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.PatenteAduanalDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface PatenteAduanalService{

    String INTERNAL_ERROR_MESSAGE = "Error al procesar Patentes";

    /**
    * Method to add new PatenteAduanal in database
    * @param patenteAduanalDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    PatenteAduanalDTO addPatenteAduanal(PatenteAduanalDTO patenteAduanalDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param patenteAduanalDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    PatenteAduanalDTO updatePatenteAduanal(PatenteAduanalDTO patenteAduanalDTO) throws CatalogException ;

    /**
    * Get details of PatenteAduanal
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    PatenteAduanalDTO findById(String id) throws CatalogException;

    /**
    * Get List of all PatenteAduanal
    * throws CatalogException if an error happens in select transaction
    */
    Collection<PatenteAduanalDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<PatenteAduanalDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
