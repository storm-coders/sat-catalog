package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.TipoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoComprobanteRepository extends
JpaRepository<TipoComprobante,String>, JpaSpecificationExecutor<TipoComprobante> {}
