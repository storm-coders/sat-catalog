package com.conta.cloud.sat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Producto")
public class ProductoDTO {
    
    @ApiModelProperty(name = "id", value = "Id de producto conforme al catálogo del SAT")
    private String id;

    @ApiModelProperty(name = "descripcion", value = "Descripción de prodcuto")
    private String descripcion;

    @ApiModelProperty(name = "incluirIva", value = "Incluir IVA")
    private String incluirIva;

    @ApiModelProperty(name = "incluirIeps", value = "Incluir IEPS")
    private String incluirIeps;

    @ApiModelProperty(name = "fechaInicio", value = "Fecha de inicio")
    private String fechaInicio;

    @ApiModelProperty(name = "fechaFin", value = "Fecha de fin")
    private String fechaFin;

    @ApiModelProperty(name = "estimuloFrontera", value = "Aplica estimulo en frontera")
    private Boolean estimuloFrontera;
}
