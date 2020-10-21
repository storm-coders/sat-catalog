package com.conta.cloud.sat.service;

import java.util.Collection;
import com.conta.cloud.sat.dto.PaisDTO;
import com.conta.cloud.sat.exception.CatalogException;

public interface PaisService {
    String INTERNAL_ERROR_MESSAGE = "Error al leer catálogo de paises";
    Collection<PaisDTO> findPaises() throws  CatalogException;
}