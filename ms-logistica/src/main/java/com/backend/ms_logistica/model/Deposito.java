package com.backend.ms_logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "DEPOSITO")
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deposito")
    private Integer idDeposito;

    @NotBlank(message = "El nombre del depósito es obligatorio")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;

    @NotNull(message = "La latitud es obligatoria")
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @Column(name = "costo_estadia_diario")
    private Double costoEstadiaDiario;

    public Deposito() {}

    public Deposito(String nombre, String direccion, Double latitud,
                    Double longitud, Double costoEstadiaDiario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.costoEstadiaDiario = costoEstadiaDiario;
    }

    // Getters y setters
    public Integer getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getCostoEstadiaDiario() {
        return costoEstadiaDiario;
    }

    public void setCostoEstadiaDiario(Double costoEstadiaDiario) {
        this.costoEstadiaDiario = costoEstadiaDiario;
    }

    /**
     * Calcula el costo de estadía basado en días
     */
    public Double calcularCostoEstadia(int dias) {
        if (costoEstadiaDiario == null || dias <= 0) {
            return 0.0;
        }
        return costoEstadiaDiario * dias;
    }

    /**
     * Calcula distancia a otro punto (fórmula de Haversine simplificada)
     * Retorna distancia en kilómetros
     */
    public Double calcularDistanciaA(Double otraLat, Double otraLon) {
        final int RADIO_TIERRA_KM = 6371;

        double dLat = Math.toRadians(otraLat - this.latitud);
        double dLon = Math.toRadians(otraLon - this.longitud);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitud)) *
                        Math.cos(Math.toRadians(otraLat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    @Override
    public String toString() {
        return "Deposito{" +
                "idDeposito=" + idDeposito +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}