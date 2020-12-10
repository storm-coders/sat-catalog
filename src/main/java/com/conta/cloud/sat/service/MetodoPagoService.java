package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.MetodoPagoDTO;
import java.util.Collection;
import org.springframework.data.domain.Page;
/**
* Versions 1.0
* Created using Rest API Generator
* Basic CRUD service
*/
public interface MetodoPagoService {

    String INTERNAL_ERROR_MESSAGE = "Error al procesar Metodos de Pago";

    /**
    * Method to add new MetodoPago in database
    * @param metodoPagoDTO the values to be saved
    * @throws CatalogException if an error happens in transaction
    */
    MetodoPagoDTO addMetodoPago(MetodoPagoDTO metodoPagoDTO) throws CatalogException;

    /**
    * Method to update values of entity
    * @param metodoPagoDTO the values to update
    * @throws CatalogException if an error happens in transaction
    */
    MetodoPagoDTO updateMetodoPago(MetodoPagoDTO metodoPagoDTO) throws CatalogException ;

    /**
    * Get details of MetodoPago
    * @param id unique identifier to find object
    * throws CatalogException if an error happens in select transaction
    */
    MetodoPagoDTO findById(String id) throws CatalogException;

    /**
    * Get List of all MetodoPago
    * throws CatalogException if an error happens in select transaction
    */
    Collection<MetodoPagoDTO> findAll() throws CatalogException;

    /**
    * Get paginated result
    * @param page -> the page to be extracted from database
    * @param size -> the size of dataset
    * @param columnToOrder -> The column to sort result
    * @param orderType -> values {'DESC','ASC'}
    * @return the page obtained from database
    * @throws CatalogException if an error happens in query select
    */
    Page<MetodoPagoDTO> getPaginatedResult(
        Integer page,Integer size,String columnToOrder, String orderType
    ) throws CatalogException ;

}
