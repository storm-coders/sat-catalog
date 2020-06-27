package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.conta.cloud.sat.domain.Aduana;
import com.conta.cloud.sat.dto.AduanaDTO;

@Mapper(componentModel = "spring")
public interface AduanaMapper {

	@Mappings({
		@Mapping(source = "idAduana", target = "id")
	})
	AduanaDTO fromEntity(Aduana aduana);
}
