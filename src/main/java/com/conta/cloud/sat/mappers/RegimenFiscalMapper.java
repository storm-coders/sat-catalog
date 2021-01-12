package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.RegimenFiscalDTO;
import com.conta.cloud.sat.domain.RegimenFiscal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface RegimenFiscalMapper {

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(regimenFiscal.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(regimenFiscal.getFechaFin()))")
    })
    RegimenFiscalDTO fromEntity(RegimenFiscal regimenFiscal);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(regimenFiscalDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(regimenFiscalDTO.getFechaFin()))")
    })
    RegimenFiscal fromDTO(RegimenFiscalDTO regimenFiscalDTO);
}
