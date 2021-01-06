package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "País")
public class PaisDTO {

    @ApiModelProperty(name = "id", value = "Código de País", required = true)
    private String id;

    @ApiModelProperty(name = "nombre", value = "Nombre de País", required = true)
    private String nombre;
}
