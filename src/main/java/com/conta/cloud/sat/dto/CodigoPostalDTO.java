package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Codigo Postal de Mexico")
public class CodigoPostalDTO {
	
	@ApiModelProperty(name = "id", value = "Codigo Postal", required = true)
	private String id;
	
	@ApiModelProperty(name = "idEstado", value = "Estado de la republica", required = true)
	private String idEstado;
	
	@ApiModelProperty(name = "codigoMunicipio", value = "Codigo del Municipio", required = false)
	private String codigoMunicipio;
	
	@ApiModelProperty(name = "estimuloEnFrontera", value = "Se aplican estimulos fronterizos", required = true)
	private Boolean estimuloEnFrontera;
	
	@ApiModelProperty(name = "fechaInicio", value = "Fecha inicial para utilizar el codigo postal", required = true)
	private String fechaInicio;
	
	@ApiModelProperty(name = "fechaFin", value = "Fecha final para utilizar el codigo postal", required = false)
	private String fechaFin;
}
