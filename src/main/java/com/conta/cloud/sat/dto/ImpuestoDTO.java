package com.conta.cloud.sat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Impuesto")
public class ImpuestoDTO {
   
   @ApiModelProperty(name = "id", value = "Id de Impuesto")
   private String id;

   @ApiModelProperty(name = "nombre", value = "Nombre de Impuesto")
   private String nombre;

   @ApiModelProperty(name = "retencion", value = "Es Retencion")
   private Boolean retencion;

   @ApiModelProperty(name = "traslado", value = "Es Traslado")
   private Boolean traslado;

   @ApiModelProperty(name = "tipo", value = "FEDERAL/ESTATAL")
   private String tipo;

   @ApiModelProperty(name = "entidad", value = "Entidad")
   private String entidad;
}
