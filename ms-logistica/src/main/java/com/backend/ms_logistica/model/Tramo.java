package com.backend.ms_logistica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRAMO")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tramo")
    private Integer idTramo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta", nullable = false)
    @JsonBackReference
    private Ruta ruta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tramo", nullable = false)
    private TipoTramo tipoTramo;

    // Depósitos opcionales según tipo de tramo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deposito_inicio")
    private Deposito depositoInicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deposito_fin")
    private Deposito depositoFin;

    // Coordenadas alternativas (cuando no hay depósito)
    @Column(name = "inicio_lat")
    private Double inicioLat;

    @Column(name = "inicio_lon")
    private Double inicioLon;

    @Column(name = "fin_lat")
    private Double finLat;

    @Column(name = "fin_lon")
    private Double finLon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    @Column(name = "distancia_km")
    private Double distanciaKm;

    @Column(name = "tiempo_estimado")
    private Double tiempoEstimado; // En horas

    @Column(name = "costo_estimado")
    private Double costoEstimado;

    @Column(name = "costo_real")
    private Double costoReal;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patente_camion")
    private Camion camion;

    public Tramo() {}

    // ======================================
    // Métodos auxiliares para coordenadas
    // ======================================

    /**
     * Obtiene la latitud de inicio, priorizando depósito si existe
     */
    public Double getLatitudInicio() {
        if (depositoInicio != null) {
            return depositoInicio.getLatitud();
        }
        return inicioLat;
    }

    /**
     * Obtiene la longitud de inicio, priorizando depósito si existe
     */
    public Double getLongitudInicio() {
        if (depositoInicio != null) {
            return depositoInicio.getLongitud();
        }
        return inicioLon;
    }

    /**
     * Obtiene la latitud de fin, priorizando depósito si existe
     */
    public Double getLatitudFin() {
        if (depositoFin != null) {
            return depositoFin.getLatitud();
        }
        return finLat;
    }

    /**
     * Obtiene la longitud de fin, priorizando depósito si existe
     */
    public Double getLongitudFin() {
        if (depositoFin != null) {
            return depositoFin.getLongitud();
        }
        return finLon;
    }

    // ======================================
    // Getters y Setters
    // ======================================

    public Integer getIdTramo() {
        return idTramo;
    }

    public void setIdTramo(Integer idTramo) {
        this.idTramo = idTramo;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public TipoTramo getTipoTramo() {
        return tipoTramo;
    }

    public void setTipoTramo(TipoTramo tipoTramo) {
        this.tipoTramo = tipoTramo;
    }

    public Deposito getDepositoInicio() {
        return depositoInicio;
    }

    public void setDepositoInicio(Deposito depositoInicio) {
        this.depositoInicio = depositoInicio;
    }

    public Deposito getDepositoFin() {
        return depositoFin;
    }

    public void setDepositoFin(Deposito depositoFin) {
        this.depositoFin = depositoFin;
    }

    public Double getInicioLat() {
        return inicioLat;
    }

    public void setInicioLat(Double inicioLat) {
        this.inicioLat = inicioLat;
    }

    public Double getInicioLon() {
        return inicioLon;
    }

    public void setInicioLon(Double inicioLon) {
        this.inicioLon = inicioLon;
    }

    public Double getFinLat() {
        return finLat;
    }

    public void setFinLat(Double finLat) {
        this.finLat = finLat;
    }

    public Double getFinLon() {
        return finLon;
    }

    public void setFinLon(Double finLon) {
        this.finLon = finLon;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public Double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Double tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public Double getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(Double costoReal) {
        this.costoReal = costoReal;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }
}