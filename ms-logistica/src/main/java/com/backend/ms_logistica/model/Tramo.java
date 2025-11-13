package com.backend.ms_logistica.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "TRAMO")
public class Tramo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRAMO")
    private Integer id_tramo;
    @Column(name = "DISTANCIA_KM")
    private Double distance_km;
    @Column(name = "COSTO_APROXIMADO")
    private Double costo_aproximado;
    @Column(name = "COSTO_REAL")
    private Double costo_real;
    @Column(name = "FECHA_HORA_INICIO")
    private Date fecha_hora_inicio;
    @Column(name = "FECHA_HORA_FIN")
    private Date fecha_hora_fin;
    @ManyToOne
    @JoinColumn(name = "ID_RUTA")
    private Ruta ruta;
    @ManyToOne
    @JoinColumn(name = "DOMINIO")
    private Camion camion;
    @ManyToOne
    @JoinColumn(name = "ID_TIPOTRAMO")
    private TipoTramo tipo_tramo;
    @ManyToOne
    @JoinColumn(name = "ID_ORIGEN")
    private Ciudad origen;
    @ManyToOne
    @JoinColumn(name = "ID_DESTINO")
    private Ciudad destino;
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO")
    private Estado estado;

    public Tramo() {
    }

    public Tramo(Double distance_km, Double costo_aproximado, Double costo_real, Date fecha_hora_inicio, Date fecha_hora_fin, Ruta ruta, Camion camion, TipoTramo tipo_tramo, Ciudad origen, Ciudad destino, Estado estado) {
        this.distance_km = distance_km;
        this.costo_aproximado = costo_aproximado;
        this.costo_real = costo_real;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.ruta = ruta;
        this.camion = camion;
        this.tipo_tramo = tipo_tramo;
        this.origen = origen;
        this.destino = destino;
        this.estado = estado;
    }

    public Integer getId_tramo() {
        return id_tramo;
    }

    public void setId_tramo(Integer id_tramo) {
        this.id_tramo = id_tramo;
    }

    public Double getDistance_km() {
        return distance_km;
    }

    public void setDistance_km(Double distance_km) {
        this.distance_km = distance_km;
    }

    public Double getCosto_aproximado() {
        return costo_aproximado;
    }

    public void setCosto_aproximado(Double costo_aproximado) {
        this.costo_aproximado = costo_aproximado;
    }

    public Double getCosto_real() {
        return costo_real;
    }

    public void setCosto_real(Double costo_real) {
        this.costo_real = costo_real;
    }

    public Date getFecha_hora_inicio() {
        return fecha_hora_inicio;
    }

    public void setFecha_hora_inicio(Date fecha_hora_inicio) {
        this.fecha_hora_inicio = fecha_hora_inicio;
    }

    public Date getFecha_hora_fin() {
        return fecha_hora_fin;
    }

    public void setFecha_hora_fin(Date fecha_hora_fin) {
        this.fecha_hora_fin = fecha_hora_fin;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public TipoTramo getTipo_tramo() {
        return tipo_tramo;
    }

    public void setTipo_tramo(TipoTramo tipo_tramo) {
        this.tipo_tramo = tipo_tramo;
    }

    public Ciudad getOrigen() {
        return origen;
    }

    public void setOrigen(Ciudad origen) {
        this.origen = origen;
    }

    public Ciudad getDestino() {
        return destino;
    }

    public void setDestino(Ciudad destino) {
        this.destino = destino;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
