package com.conta.cloud.sat.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CodigoPostalId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3679747840522190055L;

	private String idCodigoPostal;
	
	private String idEstado;
}
