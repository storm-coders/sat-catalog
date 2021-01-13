package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.TipoComprobanteDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface TipoComprobanteService{

    // TODO add specific message
    String INTERNAL_ERROR_MESSAGE = "";

    /**
    * Method to add new TipoComprobante in database
    * @param tipoComprobanteDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    TipoComprobanteDTO addTipoComprobante(TipoComprobanteDTO tipoComprobanteDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param tipoComprobanteDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    TipoComprobanteDTO updateTipoComprobante(TipoComprobanteDTO tipoComprobanteDTO) throws CatalogException ;

    /**
    * Get details of TipoComprobante
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    TipoComprobanteDTO findById(String id) throws CatalogException;

    /**
    * Get List of all TipoComprobante
    * throws CatalogException if an error happens in select transaction
    */
    Collection<TipoComprobanteDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<TipoComprobanteDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
