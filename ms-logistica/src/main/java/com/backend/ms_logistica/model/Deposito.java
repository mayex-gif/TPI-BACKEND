package com.backend.ms_logistica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DEPOSITO")
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deposito")
    private Integer idDeposito;

    @Column(nullable = false, name = "nombre")
    private String nombre;

    @Column(nullable = false, name = "direccion")
    private String direccion;

    @Column(nullable = false, name = "latitud")
    private Double latitud;

    @Column(nullable = false, name = "longitud")
    private Double longitud;

    @Column(name = "costo_estadia_diario")
    private Double costoEstadiaDiario;

    public Deposito() {}

    public Deposito(String nombre, String direccion, Double latitud, Double longitud, Double costoEstadiaDiario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.costoEstadiaDiario = costoEstadiaDiario;
    }

    // Getters y setters
    public Integer getIdDeposito() { return idDeposito; }
    public void setIdDeposito(Integer idDeposito) { this.idDeposito = idDeposito; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Double getCostoEstadiaDiario() {
        return costoEstadiaDiario;
    }

    public void setCostoEstadiaDiario(Double costoEstadiaDiario) {
        this.costoEstadiaDiario = costoEstadiaDiario;
    }
}
