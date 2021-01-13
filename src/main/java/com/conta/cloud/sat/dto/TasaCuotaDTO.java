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
@ApiModel(description = "TasaCuota")
public class TasaCuotaDTO{

	@ApiModelProperty(name = "id", value = "Id de tasa/cuota")
	private String id;

	@ApiModelProperty(name = "tipo", value = "Tipo {Rango, Fijo}")
	private String tipo;

	@ApiModelProperty(name = "valorMinimo", value = "Valor minimo de tasa/cuota")
	private BigDecimal valorMinimo;

	@ApiModelProperty(name = "valorMaximo", value = "Valor maximo de tasa/cuota")
	private BigDecimal valorMaximo;

	@ApiModelProperty(name = "factor", value = "Tipo de factor {Tasa, Cuota}")
	private String factor;

	@ApiModelProperty(name = "traslado", value = "Aplicar a traslado")
	private Boolean traslado;

	@ApiModelProperty(name = "retencion", value = "Aplicar a retencion")
	private Boolean retencion;

	@ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio de tasa/cuota")
	private String fechaInicio;

	@ApiModelProperty(name = "fechaFin", value = "Fecha de fin de tasa/cuota")
	private String fechaFin;

	@ApiModelProperty(name = "impuesto", value = "Impuesto aplicable")
	private String impuesto;

}

