package com.conta.cloud.sat.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_tasa_cuota")
public class TasaCuota {

    @Id
    @Column(name= "id_tasa_cuota")
    private String id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor_minimo")
    private BigDecimal valorMinimo;

    @Column(name = "valor_maximo")
    private BigDecimal valorMaximo;

    @Column(name = "factor")
    private String factor;

    @Column(name = "traslado")
    private Boolean traslado;

    @Column(name = "retencion")
    private Boolean retencion;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "impuesto")
    private String impuesto;
}
