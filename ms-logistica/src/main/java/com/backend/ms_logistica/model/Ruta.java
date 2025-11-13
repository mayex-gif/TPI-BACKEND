package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RUTA")
public class Ruta {
    //ID_RUTA INT PRIMARY KEY AUTO_INCREMENT,
    //    ID_SOLICITUD INT,
    //    CANTIDAD_TRAMOS INT,
    //    CANTIDAD_DEPOSITOS INT,
    //    DISTANCIA_TOTAL DOUBLE,
    //    FOREIGN KEY (ID_SOLICITUD) REFERENCES SOLICITUD(ID_SOLICITUD)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RUTA")
    private Integer id_ruta;
    @Column(name = "CANTIDAD_TRAMOS")
    private int cantidad_tramos;
    @Column(name = "CANTIDAD_DEPOSITOS")
    private int cantidad_depositos;
    @Column(name = "DISTANCIA_TOTAL")
    private Double distance_total;
    //@ManyToOne
    //@JoinColumn(name = "ID_SOLICITUD")
    @Column(name = "ID_SOLICITUD")
    private Integer idSolicitud;

    public Ruta() {
    }

    public Ruta(int cantidad_tramos, int cantidad_depositos, Double distance_total, Integer idSolicitud) {
        this.cantidad_tramos = cantidad_tramos;
        this.cantidad_depositos = cantidad_depositos;
        this.distance_total = distance_total;
        this.idSolicitud = idSolicitud;
    }

    public Integer getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(Integer id_ruta) {
        this.id_ruta = id_ruta;
    }

    public int getCantidad_tramos() {
        return cantidad_tramos;
    }

    public void setCantidad_tramos(int cantidad_tramos) {
        this.cantidad_tramos = cantidad_tramos;
    }

    public int getCantidad_depositos() {
        return cantidad_depositos;
    }

    public void setCantidad_depositos(int cantidad_depositos) {
        this.cantidad_depositos = cantidad_depositos;
    }

    public Double getDistance_total() {
        return distance_total;
    }

    public void setDistance_total(Double distance_total) {
        this.distance_total = distance_total;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
