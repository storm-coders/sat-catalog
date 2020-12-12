package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MonedaRepository extends
JpaRepository<Moneda,String>, JpaSpecificationExecutor{}
