package com.conta.cloud.sat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("InvluirPropiedad Converter Testing")
public class IncluirPropiedadConverterTest {
   private IncluirPropiedadConverter converter = new IncluirPropiedadConverter();

   @Test
   public void convertToDatabaseColumn_propiedadIsNull_valueIsNull() {
      String value = converter.convertToDatabaseColumn(null);
      assertNull(value);
   }

   @Test
   public void convertToDatabaseColumn_propiedadIsNotNull_valueIsNotNull() {
      String value = converter.convertToDatabaseColumn(IncluirPropiedad.OPT);
      assertEquals("opcional", value);
   }

   @Test
   public void convertToEntityAttribute_optionIsNull_propiedadIsDefault() {
      IncluirPropiedad propiedad = converter.convertToEntityAttribute(null);
      assertEquals(IncluirPropiedad.OPT, propiedad);
   }

   @Test
   public void convertToEntityAttribute_optionsIsNotEmpty_shouldConvertToPropiedad() {
      IncluirPropiedad propiedad = converter.convertToEntityAttribute("SI");
      assertEquals(IncluirPropiedad.YES, propiedad);
   }
}
