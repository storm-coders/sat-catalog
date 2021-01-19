package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.TipoRelacionDTO;
import com.conta.cloud.sat.domain.TipoRelacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface TipoRelacionMapper {

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(tipoRelacion.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(tipoRelacion.getFechaFin()))")
    })
    TipoRelacionDTO fromEntity(TipoRelacion tipoRelacion);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(tipoRelacionDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(tipoRelacionDTO.getFechaFin()))")
    })
    TipoRelacion fromDTO(TipoRelacionDTO tipoRelacionDTO);
}
