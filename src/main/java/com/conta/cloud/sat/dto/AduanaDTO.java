package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Aduana")
public class AduanaDTO {

	@ApiModelProperty(name = "id", value = "Id de Aduana")
	private String id;

	@ApiModelProperty(name = "descripcion", value = "Descripcoión de Aduana")
	private String descripcion;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de Inicio")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de Fin")
	private String fechaFin;
}
