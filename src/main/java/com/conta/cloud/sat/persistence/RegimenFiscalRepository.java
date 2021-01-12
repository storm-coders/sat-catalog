package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.RegimenFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegimenFiscalRepository extends
JpaRepository<RegimenFiscal,String>, JpaSpecificationExecutor<RegimenFiscal> {}
