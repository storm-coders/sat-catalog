package com.conta.cloud.sat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

@DisplayName("IncluirPropiedad testing")
public class IncluirPropiedadTest {
   
   @ParameterizedTest
   @MethodSource("incluirPropProvider")
   public void fromString_shouldReturnValidEnum(String option, IncluirPropiedad expected) {
      IncluirPropiedad propiedad = IncluirPropiedad.fromString(option);
      assertEquals(expected, propiedad);
   }

   @Test
   public void fromString_optionIsEmpty_shouldThrowException() {
   	IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
		IncluirPropiedad.fromString("");
		});
	assertEquals("Invalid parameter", ex.getMessage());
   }

   static Stream<Arguments> incluirPropProvider() {
      return Stream.of(
		         arguments(null, IncluirPropiedad.OPT),
			 arguments("si", IncluirPropiedad.YES),
			 arguments("sí", IncluirPropiedad.YES),
			 arguments("no", IncluirPropiedad.NO),
			 arguments("opcional", IncluirPropiedad.OPT)
		      );
   
   }
}
