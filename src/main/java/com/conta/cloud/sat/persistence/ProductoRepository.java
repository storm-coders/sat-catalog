package com.conta.cloud.sat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.conta.cloud.sat.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String>,
    JpaSpecificationExecutor<Producto>  {
    
}
