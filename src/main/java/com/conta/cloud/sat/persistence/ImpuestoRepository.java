package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.Impuesto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpuestoRepository extends JpaRepository<Impuesto, String> {

}
