package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
	@ApiModelProperty(name = "id", value = "Id del Estado")
	private String id;
	
	@ApiModelProperty(name = "nombre", value = "Nombre del Estado")
	private String nombre;
	
	@ApiModelProperty(name = "idPais", value = "País al que pertenece el Estado")
	private String idPais;
}
