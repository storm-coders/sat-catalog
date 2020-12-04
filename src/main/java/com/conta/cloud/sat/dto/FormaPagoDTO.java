package com.conta.cloud.sat.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Forma de Pago")
public class FormaPagoDTO {

   @ApiModelProperty(name = "id", value = "Id de Forma de Pago")
   private String id;

   @ApiModelProperty(name = "descripcion", value = "Descripción de Forma de Pago")
   private String descripcion;

   @ApiModelProperty(name = "bancarizado", value = "Incluir datos de banco")
   private IncluirPropiedadDTO bancarizado;

   @ApiModelProperty(name = "numeroOperacion", value = "Incluir Número de Operación")
   private IncluirPropiedadDTO numeroOperacion;

   @ApiModelProperty(name = "rfcCtaOrdenante", value = "Incluir RFC de Cuenta Ordenante")
   private IncluirPropiedadDTO rfcCtaOrdenante;

   @ApiModelProperty(name = "ctaOrdenante", value = "Incluir Cuenta Ordenante")
   private IncluirPropiedadDTO ctaOrdenante;

   @ApiModelProperty(name = "regexCtaOrdenante", value = "Regex de Cuenta Ordenante")
   private String regexCtaOrdenante;

   @ApiModelProperty(name = "rfcCtaBeneficiario", value = "Incluir RFC de Cuenta Beneficiario")
   private IncluirPropiedadDTO rfcCtaBeneficiario;

   @ApiModelProperty(name="regexCtaBeneficiario", value = "Regex para Cuenta de Beneficiario")
   private String regexCtaBeneficiario;

   @ApiModelProperty(name = "tipoCadena", value = "Incluir el tipo de cadena")
   private IncluirPropiedadDTO tipoCadena;

   @ApiModelProperty(name = "fechaInicio", value = "Fecha de Inicio")
   private String fechaInicio;

   @ApiModelProperty(name = "fechaFin", value = "Fecha Fin")
   private String fechaFin;
}
