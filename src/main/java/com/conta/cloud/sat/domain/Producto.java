package com.conta.cloud.sat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_product_servicio")
public class Producto implements Serializable{

    @Id
    @Column(name = "id_producto_servicio")
    private String idProductoServicio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "incluir_iva_traslado")
    private IncluirPropiedad incluirIvaTraslado;

    @Column(name = "incluir_ieps_traslado")
    private IncluirPropiedad incluirIepsTraslado;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "estimulo_frontera")
    private Boolean estimuloFrontera;
}
