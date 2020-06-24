package com.conta.cloud.sat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.conta.cloud.sat.domain.CodigoPostal;
import com.conta.cloud.sat.domain.CodigoPostalId;

public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, CodigoPostalId>, // 
    JpaSpecificationExecutor<CodigoPostal>{

}
