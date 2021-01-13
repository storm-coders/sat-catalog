package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.TasaCuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TasaCuotaRepository extends
JpaRepository<TasaCuota,String>, JpaSpecificationExecutor<TasaCuota> {}
