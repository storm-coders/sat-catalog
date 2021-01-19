package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.UsoCFDIDTO;
import com.conta.cloud.sat.domain.UsoCFDI;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UsoCFDIMapper {

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(usoCFDI.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(usoCFDI.getFechaFin()))")
    })
    UsoCFDIDTO fromEntity(UsoCFDI usoCFDI);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(usoCFDIDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(usoCFDIDTO.getFechaFin()))")
    })
    UsoCFDI fromDTO(UsoCFDIDTO usoCFDIDTO);
}
