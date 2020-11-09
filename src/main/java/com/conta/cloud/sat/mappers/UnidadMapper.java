package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.domain.Unidad;

@Mapper(componentModel = "spring")
public interface UnidadMapper {

    @Mappings({
        @Mapping(source = "idUnidad", target = "id"),
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(unidad.getFechaInicio()))"),
		@Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(unidad.getFechaFin()))")
    })
    UnidadDTO fromEntity(Unidad unidad);

}