package com.conta.cloud.sat.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.conta.cloud.sat.domain.Aduana;

public interface AduanaRepository //
 	extends JpaRepository<Aduana, String>, JpaSpecificationExecutor<Aduana>{

}
