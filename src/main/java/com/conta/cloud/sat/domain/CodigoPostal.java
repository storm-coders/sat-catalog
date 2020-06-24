package com.conta.cloud.sat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ct_codigo_postal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodigoPostal implements Serializable {
	
	private static final long serialVersionUID = 1381193027986451047L;

	@EmbeddedId
	private CodigoPostalId id;
	
	@Column(name = "codigo_municipio")
	private String codigoMunicipio;
	
	@Column(name = "estimulo_frontera")
	private Boolean estimuloFrontera;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;
}
