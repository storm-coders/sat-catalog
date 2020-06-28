package com.conta.cloud.sat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conta.cloud.sat.domain.Municipio;

public interface MunicipiosRepository extends JpaRepository<Municipio, String> {

	List<Municipio> findByIdEstado(String idEstado);
	
}
