package com.conta.cloud.sat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Unidad")
public class UnidadDTO {

    @ApiModelProperty(name = "id", value = "Id de Unidad")	
    private String id;

    @ApiModelProperty(name = "nombre", value = "Nombre de unidad de medida")
    private String nombre;

    @ApiModelProperty(name = "simbolo", value = "Simbolo de unidad de medida")
    private String simbolo;

    @ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio")
    private String fechaInicio;

    @ApiModelProperty(name = "fechaFin", value = "Fecha de fin")
    private String fechaFin;
}
