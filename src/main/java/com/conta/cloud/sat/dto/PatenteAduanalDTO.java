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
@ApiModel(description = "PatenteAduanal")
public class PatenteAduanalDTO{

	@ApiModelProperty(name = "id", value = "Id de patente aduanal")
	private String id;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio de patente aduanal")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin de patente aduanal")
	private String fechaFin;

}
