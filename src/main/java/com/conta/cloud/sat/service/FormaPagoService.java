package com.conta.cloud.sat.service;

import com.conta.cloud.sat.dto.FormaPagoDTO;
import com.conta.cloud.sat.exception.CatalogException;

import java.util.Collection;

public interface FormaPagoService {
   String INTERNAL_ERROR_MESSAGE = "Error al leer catálogo de Formas de Pago";	
   Collection<FormaPagoDTO> findAll() throws CatalogException;
}
