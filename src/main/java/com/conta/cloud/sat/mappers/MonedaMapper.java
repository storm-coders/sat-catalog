package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.MonedaDTO;
import com.conta.cloud.sat.domain.Moneda;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MonedaMapper {
    @Mappings({
	@Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(moneda.getFechaInicio()))"),
	@Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(moneda.getFechaFin()))")
    })
    MonedaDTO fromEntity(Moneda moneda);

    @Mappings({
	@Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(monedaDTO.getFechaInicio()))"),
	@Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(monedaDTO.getFechaFin()))")
    })
    Moneda fromDTO(MonedaDTO monedaDTO);
}
