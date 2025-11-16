package com.backend.ms_logistica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRAMO")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tramo")
    private Integer idTramo;

    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false)
    private Ruta ruta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tramo", nullable = false)
    private TipoTramo tipoTramo;

    @Column(name = "id_origen", nullable = false)
    private Integer idOrigen;

    @Column(name = "id_destino", nullable = false)
    private Integer idDestino;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    @Column(name = "distancia_km")
    private Double distanciaKm;

    @Column(name = "tiempo_estimado")
    private Double tiempoEstimado; // en minutos

    @Column(name = "costo_aprox")
    private Double costoAprox;

    @Column(name = "costo_real")
    private Double costoReal;

    @Column(name = "tiempo_real")
    private Double tiempoReal; // en minutos

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "patente_camion")
    private Camion camion;

    @PrePersist
    public void prePersist(){
        fechaHoraInicio = LocalDateTime.now();
    }

    public Tramo() {}

    public Tramo(Ruta ruta, TipoTramo tipoTramo, Integer idOrigen, Integer idDestino, Estado estado, Double distanciaKm, Double tiempoEstimado, Double costoAprox, Double costoReal, Double tiempoReal, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Camion camion) {
        this.ruta = ruta;
        this.tipoTramo = tipoTramo;
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.estado = estado;
        this.distanciaKm = distanciaKm;
        this.tiempoEstimado = tiempoEstimado;
        this.costoAprox = costoAprox;
        this.costoReal = costoReal;
        this.tiempoReal = tiempoReal;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.camion = camion;
    }

    // getters y setters
    public Integer getIdTramo() { return idTramo; }
    public void setIdTramo(Integer idTramo) { this.idTramo = idTramo; }
    public Ruta getRuta() { return ruta; }
    public void setRuta(Ruta ruta) { this.ruta = ruta; }
    public TipoTramo getTipoTramo() { return tipoTramo; }
    public void setTipoTramo(TipoTramo tipoTramo) { this.tipoTramo = tipoTramo; }
    public Integer getIdOrigen() { return idOrigen; }
    public void setIdOrigen(Integer idOrigen) { this.idOrigen = idOrigen; }
    public Integer getIdDestino() { return idDestino; }
    public void setIdDestino(Integer idDestino) { this.idDestino = idDestino; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Double getCostoAprox() { return costoAprox; }
    public void setCostoAprox(Double costoAprox) { this.costoAprox = costoAprox; }
    public Double getCostoReal() { return costoReal; }
    public void setCostoReal(Double costoReal) { this.costoReal = costoReal; }
    public Double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(Double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
    public Double getTiempoReal() { return tiempoReal; }
    public void setTiempoReal(Double tiempoReal) { this.tiempoReal = tiempoReal; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }
    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }
    public Camion getCamion() { return camion; }
    public void setCamion(Camion camion) { this.camion = camion; }
}
