package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.UsoCFDIDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface UsoCFDIService{

    
    String INTERNAL_ERROR_MESSAGE = "Error al procesar usos de CFDI";

    /**
    * Method to add new UsoCFDI in database
    * @param usoCFDIDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    UsoCFDIDTO addUsoCFDI(UsoCFDIDTO usoCFDIDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param usoCFDIDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    UsoCFDIDTO updateUsoCFDI(UsoCFDIDTO usoCFDIDTO) throws CatalogException ;

    /**
    * Get details of UsoCFDI
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    UsoCFDIDTO findById(String id) throws CatalogException;

    /**
    * Get List of all UsoCFDI
    * throws CatalogException if an error happens in select transaction
    */
    Collection<UsoCFDIDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<UsoCFDIDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
