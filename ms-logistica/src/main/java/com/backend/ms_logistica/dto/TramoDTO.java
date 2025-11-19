package com.backend.ms_logistica.dto;

import com.backend.ms_logistica.model.TipoTramo;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TramoDTO {

    private Integer idTramo;

    private Integer idRuta;

    @NotNull(message = "El tipo de tramo es obligatorio")
    private TipoTramo tipoTramo;

    // Depósitos opcionales
    private DepositoDTO depositoInicio;
    private DepositoDTO depositoFin;

    // Coordenadas alternativas
    private Double inicioLat;
    private Double inicioLon;
    private Double finLat;
    private Double finLon;

    private EstadoDTO estado;

    private Double distanciaKm;
    private Double tiempoEstimado;
    private Double costoEstimado;
    private Double costoReal;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    private CamionDTO camion;

    // Constructores
    public TramoDTO() {}

    // Getters y Setters
    public Integer getIdTramo() { return idTramo; }
    public void setIdTramo(Integer idTramo) { this.idTramo = idTramo; }

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public TipoTramo getTipoTramo() { return tipoTramo; }
    public void setTipoTramo(TipoTramo tipoTramo) { this.tipoTramo = tipoTramo; }

    public DepositoDTO getDepositoInicio() { return depositoInicio; }
    public void setDepositoInicio(DepositoDTO depositoInicio) {
        this.depositoInicio = depositoInicio;
    }

    public DepositoDTO getDepositoFin() { return depositoFin; }
    public void setDepositoFin(DepositoDTO depositoFin) { this.depositoFin = depositoFin; }

    public Double getInicioLat() { return inicioLat; }
    public void setInicioLat(Double inicioLat) { this.inicioLat = inicioLat; }

    public Double getInicioLon() { return inicioLon; }
    public void setInicioLon(Double inicioLon) { this.inicioLon = inicioLon; }

    public Double getFinLat() { return finLat; }
    public void setFinLat(Double finLat) { this.finLat = finLat; }

    public Double getFinLon() { return finLon; }
    public void setFinLon(Double finLon) { this.finLon = finLon; }

    public EstadoDTO getEstado() { return estado; }
    public void setEstado(EstadoDTO estado) { this.estado = estado; }

    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }

    public Double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(Double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public Double getCostoEstimado() { return costoEstimado; }
    public void setCostoEstimado(Double costoEstimado) { this.costoEstimado = costoEstimado; }

    public Double getCostoReal() { return costoReal; }
    public void setCostoReal(Double costoReal) { this.costoReal = costoReal; }

    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public CamionDTO getCamion() { return camion; }
    public void setCamion(CamionDTO camion) { this.camion = camion; }
}