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
@ApiModel(description = "UsoCFDI")
public class UsoCFDIDTO{
    
	@ApiModelProperty(name = "id", value = "Id de uso CFDI")
	private String id;

	@ApiModelProperty(name = "descripcion", value = "Descripcion para uso de CFDI")
	private String descripcion;

	@ApiModelProperty(name = "personaFisica", value = "El uso de CFDI es aplicable a persona fisica")
	private Boolean personaFisica;

	@ApiModelProperty(name = "personaMoral", value = "El uso de CFDI es aplicable a persona moral")
	private Boolean personaMoral;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio de uso de CFDI")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin de uso de CFDI")
	private String fechaFin;

}
