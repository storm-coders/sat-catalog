package com.conta.cloud.sat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadDTO {

    private String id;

    private String nombre;

    private String simbolo;

    private String fechaInicio;

    private String fechaFin;
}