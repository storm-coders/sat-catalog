package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.conta.cloud.sat.domain.CodigoPostal;
import com.conta.cloud.sat.dto.CodigoPostalDTO;

@Component
@Mapper(componentModel = "spring")
public interface CodigoPostalMapper {
	@Mappings({
		@Mapping(source = "id.idEstado", target = "idEstado"),
		@Mapping(source = "id.idCodigoPostal", target = "id"),
		@Mapping(source = "estimuloFrontera", target = "estimuloEnFrontera"),
		@Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(codigoPostal.getFechaInicio()))"),
		@Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(codigoPostal.getFechaFin()))")
	})
	CodigoPostalDTO fromEntity(CodigoPostal codigoPostal);
}
