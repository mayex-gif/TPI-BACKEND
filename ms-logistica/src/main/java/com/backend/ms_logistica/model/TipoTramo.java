package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPOTRAMO")
public class TipoTramo {
    //    ID_TIPOTRAMO INT PRIMARY KEY AUTO_INCREMENT,
    //    DESCRIPCION VARCHAR(255)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPOTRAMO")
    private Integer id_tipotramo;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public TipoTramo() {
    }

    public TipoTramo(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId_tipotramo() {
        return id_tipotramo;
    }

    public void setId_tipotramo(Integer id_tipotramo) {
        this.id_tipotramo = id_tipotramo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
