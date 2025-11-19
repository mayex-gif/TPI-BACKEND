package com.backend.ms_logistica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SOLICITUD")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    @Column(name = "fecha_solicitud", updatable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    // Coordenadas de origen
    @Column(name = "origen_direccion", length = 255)
    private String origenDireccion;

    @Column(name = "origen_lat", nullable = false)
    private Double origenLat;

    @Column(name = "origen_lon", nullable = false)
    private Double origenLon;

    // Coordenadas de destino
    @Column(name = "destino_direccion", length = 255)
    private String destinoDireccion;

    @Column(name = "destino_lat", nullable = false)
    private Double destinoLat;

    @Column(name = "destino_lon", nullable = false)
    private Double destinoLon;

    // Costos y tiempos
    @Column(name = "costo_estimado")
    private Double costoEstimado;

    @Column(name = "tiempo_estimado")
    private Double tiempoEstimado;

    @Column(name = "costo_final")
    private Double costoFinal;

    @Column(name = "tiempo_real")
    private Double tiempoReal;

    // Relación 1:1 con Contenedor (CORREGIDO)
    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contenedor contenedor;

    // Relación 1:1 con Ruta
    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ruta ruta;

    public Solicitud() {}

    @PrePersist
    public void prePersist() {
        if (this.fechaSolicitud == null) {
            this.fechaSolicitud = LocalDateTime.now();
        }
    }

    // Getters y setters
    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigenDireccion() {
        return origenDireccion;
    }

    public void setOrigenDireccion(String origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public Double getOrigenLat() {
        return origenLat;
    }

    public void setOrigenLat(Double origenLat) {
        this.origenLat = origenLat;
    }

    public Double getOrigenLon() {
        return origenLon;
    }

    public void setOrigenLon(Double origenLon) {
        this.origenLon = origenLon;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public void setDestinoDireccion(String destinoDireccion) {
        this.destinoDireccion = destinoDireccion;
    }

    public Double getDestinoLat() {
        return destinoLat;
    }

    public void setDestinoLat(Double destinoLat) {
        this.destinoLat = destinoLat;
    }

    public Double getDestinoLon() {
        return destinoLon;
    }

    public void setDestinoLon(Double destinoLon) {
        this.destinoLon = destinoLon;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public Double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public Double getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(Double costoFinal) {
        this.costoFinal = costoFinal;
    }

    public Double getTiempoReal() {
        return tiempoReal;
    }

    public void setTiempoReal(Double tiempoReal) {
        this.tiempoReal = tiempoReal;
    }

    public Contenedor getContenedor() {
        return contenedor;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}