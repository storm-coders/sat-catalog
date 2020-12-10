package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.dto.MetodoPagoDTO;
import com.conta.cloud.sat.domain.MetodoPago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MetodoPagoMapper {
    
    @Mappings({
        @Mapping(source = "idMetodoPago", target = "id"),
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(metodoPago.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(metodoPago.getFechaFin()))")
    })
    MetodoPagoDTO fromEntity(MetodoPago metodoPago);

    @Mappings({
        @Mapping(source = "id", target = "idMetodoPago"), 
        @Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(metodoPagoDTO.getFechaInicio()))"),
        @Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromString(metodoPagoDTO.getFechaFin()))")
    })
    MetodoPago fromDTO(MetodoPagoDTO metodoPagoDTO);
}
