package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.domain.Impuesto;
import com.conta.cloud.sat.dto.ImpuestoDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImpuestoMapper {

   @Mappings({
      @Mapping(source = "idImpuesto", target = "id"),
      @Mapping(source = "impuesto", target = "nombre")
   })
   public ImpuestoDTO fromEntity(Impuesto impuesto);
}
