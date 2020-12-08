package com.conta.cloud.sat.service;

import com.conta.cloud.sat.dto.ImpuestoDTO;
import com.conta.cloud.sat.exception.CatalogException;

import java.util.Collection;

public interface ImpuestoService {

   String INTERNAL_ERROR_MESSAGE  = "Error al leer catálogo de Impuestos";

   Collection<ImpuestoDTO> findAll() throws CatalogException;
}
