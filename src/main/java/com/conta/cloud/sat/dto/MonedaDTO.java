package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ApiModel(description = "Monedas")
public class MonedaDTO{
	@ApiModelProperty(name = "id", value = "Id the moneda")
	private String id;
	
    @ApiModelProperty(name = "descripcion", value = "Descripción de moneda")
	private String descripcion;

	@ApiModelProperty(name = "noDecimales", value = "No, decimales permitidos")
	private Integer noDecimales;

	@ApiModelProperty(name = "porcentajeVariacion", value = "Porcentaje de variación de moneda")
	private Integer porcentajeVariacion;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin")
	private String fechaFin;

}
