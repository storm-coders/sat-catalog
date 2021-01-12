package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.PatenteAduanalDTO;
import com.conta.cloud.sat.domain.PatenteAduanal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PatenteAduanalMapper {

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(patenteAduanal.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(patenteAduanal.getFechaFin()))")
    })
    PatenteAduanalDTO fromEntity(PatenteAduanal patenteAduanal);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(patenteAduanalDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(patenteAduanalDTO.getFechaFin()))")
    })
    PatenteAduanal fromDTO(PatenteAduanalDTO patenteAduanalDTO);
}
