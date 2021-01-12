package com.conta.cloud.sat.persistence;

import com.conta.cloud.sat.domain.PatenteAduanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatenteAduanalRepository extends
JpaRepository<PatenteAduanal,String>, JpaSpecificationExecutor<PatenteAduanal> {}
