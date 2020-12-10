package com.conta.cloud.sat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_metodo_pago")
public class MetodoPago {

   @Id
   @Column(name = "id_metodo_pago")
   private String idMetodoPago;

   @Column(name = "descripcion")
   private String descripcion;

   @Column(name = "fecha_inicio")
   private Date fechaInicio;

   @Column(name = "fecha_fin")
   private Date fechaFin;
}
