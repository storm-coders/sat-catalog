package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.conta.cloud.sat.domain.Estado;
import com.conta.cloud.sat.dto.EstadoDTO;

@Mapper(componentModel = "spring")
public interface EstadoMapper {

	@Mappings({
		@Mapping(source = "idEstado", target = "id")
	})
	EstadoDTO fromEntity(Estado estado);
}
