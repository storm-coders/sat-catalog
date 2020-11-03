package com.conta.cloud.sat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.conta.cloud.sat.dto.UnidadDTO;
import com.conta.cloud.sat.util.DateUtils;
import com.conta.cloud.sat.domain.Unidad;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface UnidadMapper {

    @Mappings({
        @Mapping(source = "idUnidad", target = "id"),
        @Mapping(source = "fechaInicio", target = "fechaInicio", qualifiedByName = "dateToString"), 
        @Mapping(source = "fechaFin", target = "fechaFin", qualifiedByName = "dateToString"),
    })
    UnidadDTO fromEntity(Unidad unidad);

    @Named("dateToString")
    default String dateToString(Date date) {
        return DateUtils.fromDate(date);
    }
}