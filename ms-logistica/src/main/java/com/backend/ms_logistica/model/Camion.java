package com.backend.ms_logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "CAMION")
public class Camion {

    @Id
    @NotBlank(message = "La patente es obligatoria")
    @Column(name = "patente", length = 20)
    private String patente;

    @NotBlank(message = "El nombre del transportista es obligatorio")
    @Column(name = "nombre_transportista", nullable = false, length = 150)
    private String nombreTransportista;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @NotNull(message = "La capacidad de peso es obligatoria")
    @Positive(message = "La capacidad de peso debe ser positiva")
    @Column(name = "capacidad_peso", nullable = false)
    private Double capacidadPeso; // En kilogramos

    @NotNull(message = "La capacidad de volumen es obligatoria")
    @Positive(message = "La capacidad de volumen debe ser positiva")
    @Column(name = "capacidad_volumen", nullable = false)
    private Double capacidadVolumen; // En metros cúbicos

    @NotNull
    @Column(name = "disponible", nullable = false)
    private Boolean disponible = true;

    @NotNull(message = "El costo base por km es obligatorio")
    @Positive(message = "El costo base debe ser positivo")
    @Column(name = "costo_base_km", nullable = false)
    private Double costoBaseKm;

    @NotNull(message = "El consumo de combustible es obligatorio")
    @Positive(message = "El consumo debe ser positivo")
    @Column(name = "consumo_combustible", nullable = false)
    private Double consumoCombustible; // litros/km

    public Camion() {}

    public Camion(String patente, String nombreTransportista, String telefono,
                  Double capacidadPeso, Double capacidadVolumen,
                  Double costoBaseKm, Double consumoCombustible) {
        this.patente = patente;
        this.nombreTransportista = nombreTransportista;
        this.telefono = telefono;
        this.capacidadPeso = capacidadPeso;
        this.capacidadVolumen = capacidadVolumen;
        this.costoBaseKm = costoBaseKm;
        this.consumoCombustible = consumoCombustible;
        this.disponible = true;
    }

    // Getters y Setters
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getNombreTransportista() {
        return nombreTransportista;
    }

    public void setNombreTransportista(String nombreTransportista) {
        this.nombreTransportista = nombreTransportista;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getCapacidadPeso() {
        return capacidadPeso;
    }

    public void setCapacidadPeso(Double capacidadPeso) {
        this.capacidadPeso = capacidadPeso;
    }

    public Double getCapacidadVolumen() {
        return capacidadVolumen;
    }

    public void setCapacidadVolumen(Double capacidadVolumen) {
        this.capacidadVolumen = capacidadVolumen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Double getCostoBaseKm() {
        return costoBaseKm;
    }

    public void setCostoBaseKm(Double costoBaseKm) {
        this.costoBaseKm = costoBaseKm;
    }

    public Double getConsumoCombustible() {
        return consumoCombustible;
    }

    public void setConsumoCombustible(Double consumoCombustible) {
        this.consumoCombustible = consumoCombustible;
    }

    // ======================================
    // Métodos de negocio
    // ======================================

    /**
     * Verifica si el camión puede transportar un contenedor
     * basado en peso y volumen
     */
    public boolean puedeTransportar(Double pesoContenedor, Double volumenContenedor) {
        if (pesoContenedor == null || volumenContenedor == null) {
            return false;
        }
        return pesoContenedor <= this.capacidadPeso &&
                volumenContenedor <= this.capacidadVolumen;
    }

    /**
     * Calcula el costo de un tramo basado en distancia
     * Considera: costo base por km
     */
    public Double calcularCostoTramo(Double distanciaKm) {
        if (distanciaKm == null || distanciaKm <= 0) {
            return 0.0;
        }
        return costoBaseKm * distanciaKm;
    }

    /**
     * Calcula el consumo de combustible para una distancia
     */
    public Double calcularConsumoCombustible(Double distanciaKm) {
        if (distanciaKm == null || distanciaKm <= 0) {
            return 0.0;
        }
        return consumoCombustible * distanciaKm;
    }

    /**
     * Marca el camión como ocupado
     */
    public void ocupar() {
        this.disponible = false;
    }

    /**
     * Marca el camión como disponible
     */
    public void liberar() {
        this.disponible = true;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "patente='" + patente + '\'' +
                ", nombreTransportista='" + nombreTransportista + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}