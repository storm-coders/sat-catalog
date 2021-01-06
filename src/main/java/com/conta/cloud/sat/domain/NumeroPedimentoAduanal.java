package com.conta.cloud.sat.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "ct_num_pedimento_aduana")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumeroPedimentoAduanal {

    @Id
    @Column(name = "id_num_pedimento_aduana")
    private String id;

    @Column(name  = "id_aduana")
    private String idAduana;

    @Column(name = "patente")
    private String patente;

    @Column(name = "ejercicio")
    private Integer ejercicio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
}