package com.conta.cloud.sat.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Aduana")
public class AduanaDTO {
	private String id;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
}