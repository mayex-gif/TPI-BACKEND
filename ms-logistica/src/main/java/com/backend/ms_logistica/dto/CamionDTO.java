package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CamionDTO {

    @NotBlank
    private String patente;

    @NotBlank
    private String nombreTransportista;

    private String telefono;

    @NotNull
    private Double capacidadPeso;

    @NotNull
    private Double capacidadVolumen;

    @NotNull
    private Boolean disponible;

    @NotNull
    private Double costoBaseKm;

    @NotNull
    private Double consumoCombustible;

    // Getters y setters
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getNombreTransportista() { return nombreTransportista; }
    public void setNombreTransportista(String nombreTransportista) { this.nombreTransportista = nombreTransportista; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Double getCapacidadPeso() { return capacidadPeso; }
    public void setCapacidadPeso(Double capacidadPeso) { this.capacidadPeso = capacidadPeso; }

    public Double getCapacidadVolumen() { return capacidadVolumen; }
    public void setCapacidadVolumen(Double capacidadVolumen) { this.capacidadVolumen = capacidadVolumen; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Double getCostoBaseKm() { return costoBaseKm; }
    public void setCostoBaseKm(Double costoBaseKm) { this.costoBaseKm = costoBaseKm; }

    public Double getConsumoCombustible() { return consumoCombustible; }
    public void setConsumoCombustible(Double consumoCombustible) { this.consumoCombustible = consumoCombustible; }
}
