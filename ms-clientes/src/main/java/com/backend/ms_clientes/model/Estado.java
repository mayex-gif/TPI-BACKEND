package com.backend.ms_clientes.model;

import jakarta.persistence.*;

@Table
@Entity(name = "ESTADO")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO")
    private Integer id_estado;

    @Column(name = "AMBITO")
    private String ambito;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Estado() {}
    public Estado(String ambito, String descripcion) {
        this.ambito = ambito;
        this.descripcion = descripcion;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public String getAmbito() {
        return ambito;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
