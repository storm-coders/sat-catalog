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
@ApiModel(description = "NumeroPedimentoAduanal")
public class NumeroPedimentoAduanalDTO{

	@ApiModelProperty(name = "id", value = "Id de numero de pedimento aduanal")
	private String id;

	@ApiModelProperty(name = "idAduana", value = "Id de aduana al que corresponde el No. de pedimento")
	private String idAduana;

	@ApiModelProperty(name = "patente", value = "No. de patente aduanal")
	private String patente;

	@ApiModelProperty(name = "ejercicio", value = "Ejercicio fiscal")
	private Integer ejercicio;

	@ApiModelProperty(name = "cantidad", value = "Cantidad de pedimento")
	private Integer cantidad;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio de No. pedimento")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin de No, pedimento")
	private String fechaFin;

}
