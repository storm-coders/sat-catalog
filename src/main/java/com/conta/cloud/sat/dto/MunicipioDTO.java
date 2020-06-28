package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioDTO {
	
	@ApiModelProperty(name = "id", value = "Id de municipio")
	private String id;
	
	@ApiModelProperty(name = "nombre", value = "Nombre de Municipio")
	private String nombre;
	
	@ApiModelProperty(name = "codigo", value = "Codigo de Municipio")
	private String codigo;
	
	@ApiModelProperty(name = "idEstado", value = "Id de Estado al que pertenece el Municipio")
	private String idEstado;
}
