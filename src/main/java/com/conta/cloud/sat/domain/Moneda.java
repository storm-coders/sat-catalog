package com.conta.cloud.sat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_moneda")
public class Moneda {

   @Column(name = "id_moneda")
   @Id
   private String id;

   @Column(name = "descripcion")
   private String descripcion;

   @Column(name = "no_decimales")
   private Integer noDecimales;

   @Column(name = "procentaje_variacion")
   private Integer porcentajeVariacion;

   @Column(name = "fecha_inicio")
   private Date fechaInicio;

   @Column(name = "fecha_fin")
   private Date fechaFin;
}
