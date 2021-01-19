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
@ApiModel(description = "TipoRelacion")
public class TipoRelacionDTO{

	@ApiModelProperty(name = "id", value = "Id de tipo relacion")
	private String id;

	@ApiModelProperty(name = "descripcion", value = "Descripcion de tipo relacion")
	private String descripcion;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio")
	private String fechaInicio;
	
	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin")
	private String fechaFin;

}
