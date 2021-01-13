package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.TipoComprobanteDTO;
import com.conta.cloud.sat.domain.TipoComprobante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface TipoComprobanteMapper {

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(tipoComprobante.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(tipoComprobante.getFechaFin()))")
    })
    TipoComprobanteDTO fromEntity(TipoComprobante tipoComprobante);

    @Mappings({
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(tipoComprobanteDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(tipoComprobanteDTO.getFechaFin()))")
    })
    TipoComprobante fromDTO(TipoComprobanteDTO tipoComprobanteDTO);
}
