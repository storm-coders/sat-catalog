package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.RegimenFiscalDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface RegimenFiscalService{

    String INTERNAL_ERROR_MESSAGE = "Error al procesar Regiemn Fiscal";

    /**
    * Method to add new RegimenFiscal in database
    * @param regimenFiscalDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    RegimenFiscalDTO addRegimenFiscal(RegimenFiscalDTO regimenFiscalDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param regimenFiscalDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    RegimenFiscalDTO updateRegimenFiscal(RegimenFiscalDTO regimenFiscalDTO) throws CatalogException ;

    /**
    * Get details of RegimenFiscal
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    RegimenFiscalDTO findById(String id) throws CatalogException;

    /**
    * Get List of all RegimenFiscal
    * throws CatalogException if an error happens in select transaction
    */
    Collection<RegimenFiscalDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<RegimenFiscalDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
