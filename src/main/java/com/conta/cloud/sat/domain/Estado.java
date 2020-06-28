package com.conta.cloud.sat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "id_estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {
	
	@Id
	@Column(name = "id_estado")
	private String idEstado;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name = "id_pais")
	private String idPais;
}
