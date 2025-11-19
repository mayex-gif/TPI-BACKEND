package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CamionDTO {

    @NotBlank(message = "La patente es obligatoria")
    private String patente;

    @NotBlank(message = "El nombre del transportista es obligatorio")
    private String nombreTransportista;

    private String telefono;

    @NotNull(message = "La capacidad de peso es obligatoria")
    @Positive(message = "La capacidad de peso debe ser positiva")
    private Double capacidadPeso;

    @NotNull(message = "La capacidad de volumen es obligatoria")
    @Positive(message = "La capacidad de volumen debe ser positiva")
    private Double capacidadVolumen;

    private Boolean disponible;

    @NotNull(message = "El costo base por km es obligatorio")
    @Positive(message = "El costo base debe ser positivo")
    private Double costoBaseKm;

    @NotNull(message = "El consumo de combustible es obligatorio")
    @Positive(message = "El consumo debe ser positivo")
    private Double consumoCombustible;

    // Constructores
    public CamionDTO() {}

    public CamionDTO(String patente, String nombreTransportista, String telefono,
                     Double capacidadPeso, Double capacidadVolumen, Boolean disponible,
                     Double costoBaseKm, Double consumoCombustible) {
        this.patente = patente;
        this.nombreTransportista = nombreTransportista;
        this.telefono = telefono;
        this.capacidadPeso = capacidadPeso;
        this.capacidadVolumen = capacidadVolumen;
        this.disponible = disponible;
        this.costoBaseKm = costoBaseKm;
        this.consumoCombustible = consumoCombustible;
    }

    // Getters y Setters
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getNombreTransportista() { return nombreTransportista; }
    public void setNombreTransportista(String nombreTransportista) {
        this.nombreTransportista = nombreTransportista;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Double getCapacidadPeso() { return capacidadPeso; }
    public void setCapacidadPeso(Double capacidadPeso) { this.capacidadPeso = capacidadPeso; }

    public Double getCapacidadVolumen() { return capacidadVolumen; }
    public void setCapacidadVolumen(Double capacidadVolumen) {
        this.capacidadVolumen = capacidadVolumen;
    }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Double getCostoBaseKm() { return costoBaseKm; }
    public void setCostoBaseKm(Double costoBaseKm) { this.costoBaseKm = costoBaseKm; }

    public Double getConsumoCombustible() { return consumoCombustible; }
    public void setConsumoCombustible(Double consumoCombustible) {
        this.consumoCombustible = consumoCombustible;
    }
}