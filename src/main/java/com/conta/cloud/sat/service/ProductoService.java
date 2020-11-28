package com.conta.cloud.sat.service;

import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.dto.ProductoDTO;

import java.util.Collection;

public interface ProductoService {
    String INTERNAL_ERROR_MESSAGE = "Error al leer catálogo de Productos";
    Collection<ProductoDTO> findProductos(String id, String descripcion) throws CatalogException;

}
