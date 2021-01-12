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
@ApiModel(description = "Regimen Fiscal")
public class RegimenFiscalDTO{

	@ApiModelProperty(name = "id", value = "Id de Regimen Fiscal")
	private String id;

	@ApiModelProperty(name = "descripcion", value = "Descripcion de Regimen Fiscal")
	private String descripcion;

	@ApiModelProperty(name = "personaFisica", value = "Flag para usar regimen con persona fisica")
	private Boolean personaFisica;

	@ApiModelProperty(name = "personaMoral", value = "Flag para usar regimen con persona moral")
	private Boolean personaMoral;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio de regimen fiscal")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin de regimen fiscal")
	private String fechaFin;

}
