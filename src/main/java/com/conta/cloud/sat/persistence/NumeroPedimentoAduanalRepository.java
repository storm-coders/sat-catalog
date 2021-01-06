package com.conta.cloud.sat.persistence;

import java.util.List;

import com.conta.cloud.sat.domain.NumeroPedimentoAduanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NumeroPedimentoAduanalRepository extends
JpaRepository<NumeroPedimentoAduanal,String>, JpaSpecificationExecutor<NumeroPedimentoAduanal> {
    
    List<NumeroPedimentoAduanal> findByIdAduana(String idAduana);
}
