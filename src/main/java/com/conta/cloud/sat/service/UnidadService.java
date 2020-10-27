package com.conta.cloud.sat.service;

import java.util.Collection;

import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.exception.CatalogException;

public interface UnidadService {
    
    String INTERNAL_ERROR_MESSAGE = "Error al seleccionar unidades";
    String BAD_DATE_FORMAT = "Format de fecha no valido";
    Collection<UnidadDTO> findUnidades(        
        String nombre,
        String simbolo,
        String fechaInicio
    ) throws CatalogException;
}