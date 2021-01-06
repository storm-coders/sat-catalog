package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.NumeroPedimentoAduanalDTO;
import com.conta.cloud.sat.domain.NumeroPedimentoAduanal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface NumeroPedimentoAduanalMapper {

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(numeroPedimentoAduanal.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(numeroPedimentoAduanal.getFechaFin()))")
    })
    NumeroPedimentoAduanalDTO fromEntity(NumeroPedimentoAduanal numeroPedimentoAduanal);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(numeroPedimentoAduanalDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(numeroPedimentoAduanalDTO.getFechaFin()))")
    })
    NumeroPedimentoAduanal fromDTO(NumeroPedimentoAduanalDTO numeroPedimentoAduanalDTO);
}
