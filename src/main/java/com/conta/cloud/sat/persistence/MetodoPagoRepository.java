package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagoRepository extends
JpaRepository<MetodoPago,String>, JpaSpecificationExecutor<MetodoPago> {}
