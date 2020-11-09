package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.conta.cloud.sat.domain.Aduana;
import com.conta.cloud.sat.dto.AduanaDTO;

@Mapper(componentModel = "spring")
public interface AduanaMapper {

	@Mappings({
		@Mapping(source = "idAduana", target = "id"),
		@Mapping(target= "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(aduana.getFechaInicio()))"),
		@Mapping(target= "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(aduana.getFechaFin()))")
	})
	AduanaDTO fromEntity(Aduana aduana);
}
