package com.conta.cloud.sat.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_impuesto")
public class Impuesto {
   
   @Column(name = "id_impuesto")
   @Id
   private String idImpuesto;

   @Column(name = "impuesto")
   private String impuesto;

   @Column(name = "retencion")
   private Boolean retencion;

   @Column(name = "traslado")
   private Boolean traslado;

   @Column(name = "tipo")
   private String tipo;

   @Column(name = "entidad")
   private String entidad;
}
