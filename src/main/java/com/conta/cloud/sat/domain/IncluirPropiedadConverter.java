package com.conta.cloud.sat.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IncluirPropiedadConverter implements AttributeConverter<IncluirPropiedad, String> {

   @Override
   public String convertToDatabaseColumn(IncluirPropiedad incluirPropiedad) {
      if(incluirPropiedad == null) {
         return null;
      }
      return incluirPropiedad.getOption();
   }


   @Override
   public IncluirPropiedad convertToEntityAttribute(String option) {
	if(option == null)
	    return IncluirPropiedad.OPT;	
   	return IncluirPropiedad.fromString(option);
   }

}
