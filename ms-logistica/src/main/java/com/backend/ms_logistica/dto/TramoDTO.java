package com.backend.ms_logistica.dto;

import com.backend.ms_logistica.model.TipoTramo;
import jakarta.validation.constraints.NotNull;

public class TramoDTO {

    private Integer idTramo;

    @NotNull
    private Integer idRuta;

    @NotNull
    private TipoTramo tipoTramo;

    @NotNull
    private Integer idOrigen;

    @NotNull
    private Integer idDestino;

    @NotNull
    private Integer estadoId;

    private Double costoAprox;
    private Double costoReal;
    private Double tiempoEstimado;
    private Double tiempoReal;
    private Double distanciaKm;
    private String dominioCamion;

    // getters y setters
    public Integer getIdTramo() { return idTramo; }
    public void setIdTramo(Integer idTramo) { this.idTramo = idTramo; }
    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }
    public TipoTramo getTipoTramo() { return tipoTramo; }
    public void setTipoTramo(TipoTramo tipoTramo) { this.tipoTramo = tipoTramo; }
    public Integer getIdOrigen() { return idOrigen; }
    public void setIdOrigen(Integer idOrigen) { this.idOrigen = idOrigen; }
    public Integer getIdDestino() { return idDestino; }
    public void setIdDestino(Integer idDestino) { this.idDestino = idDestino; }
    public Integer getEstadoId() { return estadoId; }
    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }
    public Double getCostoAprox() { return costoAprox; }
    public void setCostoAprox(Double costoAprox) { this.costoAprox = costoAprox; }
    public Double getCostoReal() { return costoReal; }
    public void setCostoReal(Double costoReal) { this.costoReal = costoReal; }
    public Double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(Double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
    public Double getTiempoReal() { return tiempoReal; }
    public void setTiempoReal(Double tiempoReal) { this.tiempoReal = tiempoReal; }
    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }
    public String getDominioCamion() { return dominioCamion; }
    public void setDominioCamion(String dominioCamion) { this.dominioCamion = dominioCamion; }
}
