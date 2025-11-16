package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RutaDTO {

    private Integer idRuta;

    @NotBlank(message = "El origen es obligatorio")
    private String origen;

    @NotBlank(message = "El destino es obligatorio")
    private String destino;

    @NotNull(message = "La distancia es obligatoria")
    @Positive(message = "La distancia debe ser positiva")
    private Double distanciaKm;

    @NotNull(message = "El costo base es obligatorio")
    @Positive(message = "El costo base debe ser positivo")
    private Double costoBase;

    public Integer getIdRuta() { return idRuta; }
    public void setIdRuta(Integer idRuta) { this.idRuta = idRuta; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }

    public Double getCostoBase() { return costoBase; }
    public void setCostoBase(Double costoBase) { this.costoBase = costoBase; }
}
