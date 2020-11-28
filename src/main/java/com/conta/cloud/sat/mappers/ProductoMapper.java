package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.conta.cloud.sat.domain.Producto;
import com.conta.cloud.sat.dto.ProductoDTO;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    
    @Mappings({
        @Mapping(source = "idProductoServicio", target = "id"),
        @Mapping(target = "incluirIva", expression = "java(producto.getIncluirIvaTraslado().getOption())"),
        @Mapping(target = "incluirIeps", expression= "java(producto.getIncluirIepsTraslado().getOption())"),
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(producto.getFechaInicio()))"),
		@Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(producto.getFechaFin()))")
    })
    ProductoDTO fromEntity(Producto producto);
    
}
