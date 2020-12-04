package com.conta.cloud.sat.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_forma_pago")
public class FormaPago {

   @Column(name = "id_forma_pago")
   @Id
   private String idFormaPago;

   @Column(name = "descripcion")
   private String descripcion;

   @Column(name = "bancarizado")
   private IncluirPropiedad bancarizado;

   @Column(name = "no_operacion")
   private IncluirPropiedad numeroOperacion;

   @Column(name = "rfc_cta_ordenante")
   private IncluirPropiedad rfcCtaOrdenante;

   @Column(name = "cta_ordenante")
   private IncluirPropiedad ctaOrdenante;

   @Column(name = "regex_cta_ordenante")
   private String regexCtaOrdenante;

   @Column(name = "rfc_cta_beneficiario")
   private IncluirPropiedad rfcCtaBeneficiario;

   @Column(name = "regex_cta_beneficiaria")
   private String regexCtaBeneficiario;

   @Column(name = "tipo_cadena")
   private IncluirPropiedad tipoCadena;

   @Column(name = "fecha_inicio")
   private Date fechaInicio;

   @Column(name = "fecha_fin")
   private Date fechaFin;
}
