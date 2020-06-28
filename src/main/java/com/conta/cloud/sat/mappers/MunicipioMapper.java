package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.conta.cloud.sat.domain.Municipio;
import com.conta.cloud.sat.dto.MunicipioDTO;

@Mapper(componentModel = "spring")
public interface MunicipioMapper {

	@Mappings({
		@Mapping(source = "idMunicipio", target = "id")
	})
	MunicipioDTO fromEntity(Municipio municipio);
}
