package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ApiModel(description = "TipoComprobante")
public class TipoComprobanteDTO{

	@ApiModelProperty(name = "id", value = "Id del tipo de comprobante {I, E, T, N, P}")
	private String id;

	@ApiModelProperty(name = "descripcion", value = "Desc. del tipo de comprobante")
	private String descripcion;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio para el tipo de comprobante")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin para el tipo de comprobnate")
	private String fechaFin;

	@ApiModelProperty(name = "valorMaximo", value = "Valor maximo para comprobante")
	private BigDecimal valorMaximo;

}
