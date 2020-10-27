package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.domain.Unidad;

@Mapper(componentModel = "spring")
public interface UnidadMapper {

    @Mappings({
        @Mapping(source = "idUnidad", target = "id")
    })
    UnidadDTO fromEntity(Unidad unidad);
}