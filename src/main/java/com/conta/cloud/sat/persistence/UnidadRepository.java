package com.conta.cloud.sat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.conta.cloud.sat.domain.Unidad;

public interface UnidadRepository extends JpaRepository <Unidad, String>, 
    JpaSpecificationExecutor<Unidad> {

}