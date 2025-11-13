package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Table
@Entity(name = "CAMION")
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOMINIO")
    private String dominio;
    @Column(name = "NOMBRE_TRANSPORTISTA")
    private String nombre_transportista;
    @Column(name = "TELEFONO_TRANSPORTISTA")
    private String telefono_transportista;
    @Column(name = "CAPACIDAD_PESO")
    private Double capacidad_peso;
    @Column(name = "CAPACIDAD_VOLUMEN")
    private Double capacidad_volumen;
    @Column(name = "DISPONIBLE")
    private Boolean disponible;
    @Column(name = "COSTO_KM")
    private Double costo_km;
    @Column(name = "CONSUMO_PROMEDIO")
    private Double consumo_promedio;

    public Camion() {
    }

    public Camion(String dominio, String nombre_transportista, String telefono_transportista, Double capacidad_peso, Double capacidad_volumen, Boolean disponible, Double costo_km, Double consumo_promedio) {
        this.dominio = dominio;
        this.nombre_transportista = nombre_transportista;
        this.telefono_transportista = telefono_transportista;
        this.capacidad_peso = capacidad_peso;
        this.capacidad_volumen = capacidad_volumen;
        this.disponible = disponible;
        this.costo_km = costo_km;
        this.consumo_promedio = consumo_promedio;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getNombre_transportista() {
        return nombre_transportista;
    }

    public void setNombre_transportista(String nombre_transportista) {
        this.nombre_transportista = nombre_transportista;
    }

    public String getTelefono_transportista() {
        return telefono_transportista;
    }

    public void setTelefono_transportista(String telefono_transportista) {
        this.telefono_transportista = telefono_transportista;
    }

    public Double getCapacidad_peso() {
        return capacidad_peso;
    }

    public void setCapacidad_peso(Double capacidad_peso) {
        this.capacidad_peso = capacidad_peso;
    }

    public Double getCapacidad_volumen() {
        return capacidad_volumen;
    }

    public void setCapacidad_volumen(Double capacidad_volumen) {
        this.capacidad_volumen = capacidad_volumen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Double getCosto_km() {
        return costo_km;
    }

    public void setCosto_km(Double costo_km) {
        this.costo_km = costo_km;
    }

    public Double getConsumo_promedio() {
        return consumo_promedio;
    }

    public void setConsumo_promedio(Double consumo_promedio) {
        this.consumo_promedio = consumo_promedio;
    }
}
