package com.conta.cloud.sat.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ct_aduana")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aduana {
	@Id
	@Column(name = "id_aduana")
	private String idAduana;
	
	@Column(name = "desc_aduana")
	private String descripcion;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;
}
