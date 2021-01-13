package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.TasaCuotaDTO;
import com.conta.cloud.sat.domain.TasaCuota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface TasaCuotaMapper {


    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(tasaCuota.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(tasaCuota.getFechaFin()))")
    })
    TasaCuotaDTO fromEntity(TasaCuota tasaCuota);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(tasaCuotaDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(tasaCuotaDTO.getFechaFin()))")
    })
    TasaCuota fromDTO(TasaCuotaDTO tasaCuotaDTO);
}
