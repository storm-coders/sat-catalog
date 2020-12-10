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
@ApiModel(description = "Metodo de Pago")
public class MetodoPagoDTO{

	@ApiModelProperty(name = "id", value = "Id unico de metodo de pago")
	private String id;

	@ApiModelProperty(name = "descricion", value = "Descripcion del metodo de pago")
	private String descripcion;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio para metodo de pago")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin para metodo de pago")
	private String fechaFin;

}
