package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.NumeroPedimentoAduanalDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface NumeroPedimentoAduanalService{

    String INTERNAL_ERROR_MESSAGE = "Error al procesar Numero de Pedimento Aduanal";

    /**
    * Method to add new NumeroPedimentoAduanal in database
    * @param numeroPedimentoAduanalDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    NumeroPedimentoAduanalDTO addNumeroPedimentoAduanal(NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param numeroPedimentoAduanalDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    NumeroPedimentoAduanalDTO updateNumeroPedimentoAduanal(NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO) throws CatalogException ;

    /**
    * Get details of NumeroPedimentoAduanal
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    NumeroPedimentoAduanalDTO findById(String id) throws CatalogException;

    /**
    * Get List of all NumeroPedimentoAduanal
    * throws CatalogException if an error happens in select transaction
    * @param idAduana filter
    */
    Collection<NumeroPedimentoAduanalDTO> findAll(String idAduana) throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<NumeroPedimentoAduanalDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
