package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TipoRelacionDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface TipoRelacionService{

    String INTERNAL_ERROR_MESSAGE = "Error al procesar TipoRelacion";

    /**
    * Method to add new TipoRelacion in database
    * @param tipoRelacionDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    TipoRelacionDTO addTipoRelacion(TipoRelacionDTO tipoRelacionDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param tipoRelacionDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    TipoRelacionDTO updateTipoRelacion(TipoRelacionDTO tipoRelacionDTO) throws CatalogException ;

    /**
    * Get details of TipoRelacion
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    TipoRelacionDTO findById(String id) throws CatalogException;

    /**
    * Get List of all TipoRelacion
    * throws CatalogException if an error happens in select transaction
    */
    Collection<TipoRelacionDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<TipoRelacionDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
