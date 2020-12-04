package com.conta.cloud.sat.mappers;

import com.conta.cloud.sat.domain.FormaPago;
import com.conta.cloud.sat.domain.IncluirPropiedad;
import com.conta.cloud.sat.dto.FormaPagoDTO;
import com.conta.cloud.sat.dto.IncluirPropiedadDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FormaPagoMapper {

   @Mappings({
      @Mapping(source = "idFormaPago", target = "id"),
      @Mapping(target = "fechaInicio", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(formaPago.getFechaInicio()))"),
      @Mapping(target = "fechaFin", expression = "java(com.conta.cloud.sat.util.DateUtils.fromDate(formaPago.getFechaFin()))" ),
      @Mapping(target = "bancarizado",source = "bancarizado",  qualifiedByName = "incluirPropFromEntity"),
      @Mapping(target = "numeroOperacion", source = "numeroOperacion", qualifiedByName = "incluirPropFromEntity"),
      @Mapping(target = "rfcCtaOrdenante", source = "rfcCtaOrdenante", qualifiedByName = "incluirPropFromEntity"),
      @Mapping(target = "ctaOrdenante", source = "ctaOrdenante", qualifiedByName = "incluirPropFromEntity"),
      @Mapping(target = "rfcCtaBeneficiario", source = "rfcCtaBeneficiario", qualifiedByName = "incluirPropFromEntity"),
      @Mapping(target = "tipoCadena", source = "tipoCadena", qualifiedByName = "incluirPropFromEntity")
   })
   public FormaPagoDTO fromEntity(FormaPago formaPago);

   @Named("incluirPropFromEntity")
   default IncluirPropiedadDTO incluirPropFromEntity(IncluirPropiedad propiedad) {
      if(propiedad == null)
         return null;
      switch(propiedad) {
         case OPT:
            return IncluirPropiedadDTO.OPT;
	 case YES:
	    return IncluirPropiedadDTO.YES;
	 case NO:
	    return IncluirPropiedadDTO.NO;
	 default:
	    return null;
      }
   }
}
