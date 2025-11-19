package com.backend.ms_logistica.dto;

import java.util.ArrayList;
import java.util.List;

public class RutaDTO {

    private Integer idRuta;
    private Integer idSolicitud;
    private List<TramoDTO> tramos = new ArrayList<>();

    // Campos calculados
    private Integer cantidadTramos;
    private Long cantidadDepositos;
    private Double distanciaTotal;
    private Double costoEstimadoTotal;

    // Constructores
    public RutaDTO() {}

    public RutaDTO(Integer idRuta, Integer idSolicitud) {
        this.idRuta = idRuta;
        this.idSolicitud = idSolicitud;
    }

    // Getters y Setters
    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public List<TramoDTO> getTramos() { return tramos; }
    public void setTramos(List<TramoDTO> tramos) { this.tramos = tramos; }

    public Integer getCantidadTramos() { return cantidadTramos; }
    public void setCantidadTramos(Integer cantidadTramos) {
        this.cantidadTramos = cantidadTramos;
    }

    public Long getCantidadDepositos() { return cantidadDepositos; }
    public void setCantidadDepositos(Long cantidadDepositos) {
        this.cantidadDepositos = cantidadDepositos;
    }

    public Double getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(Double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public Double getCostoEstimadoTotal() { return costoEstimadoTotal; }
    public void setCostoEstimadoTotal(Double costoEstimadoTotal) {
        this.costoEstimadoTotal = costoEstimadoTotal;
    }
}