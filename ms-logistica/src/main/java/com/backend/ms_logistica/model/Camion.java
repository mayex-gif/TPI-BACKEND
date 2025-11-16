package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CAMION")
public class Camion {

    @Id
    @Column(length = 20, name = "patente")
    private String patente; // Dominio como PK

    @Column(nullable = false, name = "nombre_transportista")
    private String nombreTransportista;

    @Column(length = 20, name = "telefono")
    private String telefono;

    @Column(nullable = false, name = "capacidad_peso")
    private Double capacidadPeso;

    @Column(nullable = false, name = "capacidad_volumen")
    private Double capacidadVolumen;

    @Column(nullable = false, name = "disponible")
    private Boolean disponible;

    @Column(nullable = false, name = "costo_base_km")
    private Double costoBaseKm;

    @Column(nullable = false, name = "consumo_combustible")
    private Double consumoCombustible; // litros/km promedio

    public Camion() {}

    // Getters y Setters
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
