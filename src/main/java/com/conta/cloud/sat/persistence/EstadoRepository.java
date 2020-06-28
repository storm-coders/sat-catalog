package com.conta.cloud.sat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conta.cloud.sat.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, String> {
	
	List<Estado> findByIdPais(String idPais);
	
}
