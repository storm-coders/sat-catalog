package com.conta.cloud.sat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.conta.cloud.sat.domain.IncluirPropiedad;
import com.conta.cloud.sat.domain.Producto;
import com.conta.cloud.sat.dto.ProductoDTO;
import com.conta.cloud.sat.exception.CatalogException;
import com.conta.cloud.sat.persistence.ProductoRepository;
import com.conta.cloud.sat.service.ErrorCode;
import com.conta.cloud.sat.service.impl.ProductoServiceImpl;
import com.conta.cloud.sat.mappers.ProductoMapper;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
@DisplayName("Productos Service Testing")
public class ProductoServiceTest {
   private ProductoServiceImpl productoService;
   private ProductoMapper mapper = Mappers.getMapper(ProductoMapper.class);

   @Mock
   private ProductoRepository repository;

   private Producto entity;

   @BeforeEach
   public void setup() { 
      this.productoService = new ProductoServiceImpl(mapper, repository);
      this.entity = new Producto();
      this.entity.setIdProductoServicio("test-id");
      this.entity.setDescripcion("testing prod");
      this.entity.setIncluirIvaTraslado(IncluirPropiedad.OPT);
      this.entity.setIncluirIepsTraslado(IncluirPropiedad.YES);
      this.entity.setFechaInicio(new Date());
      this.entity.setEstimuloFrontera(Boolean.FALSE);
   }

   @Test
   public void findProductos_idAndDescripcionAreNull_shouldFetchFirst100Rows() throws Exception {    
      when(repository.findAll(any(PageRequest.class)))
	.thenReturn(new PageImpl<>(Collections.singletonList(this.entity)));	
     
      Collection<ProductoDTO> productos = this.productoService.findProductos(null, null);    
      verify(repository).findAll(any(PageRequest.class));
      assertFalse(productos.isEmpty());
   }

   @Test
   public void findProductos_idIsNotEmpty_shouldFetchUsingFilter() throws Exception {
      when(repository.findAll(any(Specification.class)))
         .thenReturn(Collections.singletonList(this.entity));

      Collection<ProductoDTO> productos = this.productoService.findProductos("testid", null);

      verify(repository).findAll(any(Specification.class));
      assertFalse(productos.isEmpty());
   }

   @Test
   public void findProductos_descripcionIsNotEmpty_shouldFetchUsingFilter() throws Exception {
      when(repository.findAll(any(Specification.class)))
         .thenReturn(Collections.singletonList(this.entity));

      Collection<ProductoDTO> productos = this.productoService.findProductos(null, "descripcion");

      verify(repository).findAll(any(Specification.class));
      assertFalse(productos.isEmpty());
   }

   @Test
   public void findProductos_errorLoadingData_shouldPropagateException() {
	when(repository.findAll(any(Specification.class)))
	     .thenThrow(new DataAccessException("Error on DB call") {
	         private static final long serialVersionUID = 1L;
	     });	
   	CatalogException exception = assertThrows(CatalogException.class, () -> {
	   this.productoService.findProductos("id-test", "desc-test");
	});
	assertEquals(ErrorCode.INTERNAL, exception.getCode());
   }
}
