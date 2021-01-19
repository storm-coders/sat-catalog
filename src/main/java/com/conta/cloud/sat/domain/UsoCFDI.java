package com.conta.cloud.sat.domain;

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
@Table(name = "ct_uso_cfdi")
public class UsoCFDI {

    @Id
    @Column(name = "id_uso_cfdi")
    private String id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "persona_fisica")
    private Boolean personaFisica;

    @Column(name = "personaMoral")
    private Boolean personaMoral;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

}
