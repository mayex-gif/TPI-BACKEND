package com.backend.ms_logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DepositoDTO {

    private Integer idDeposito;

    @NotBlank(message = "El nombre del depósito es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotNull(message = "La latitud es obligatoria")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    private Double longitud;

    private Double costoEstadiaDiario;

    public DepositoDTO() {}

    public DepositoDTO(Integer idDeposito, String nombre, String direccion, Double latitud, Double longitud, Double costoEstadiaDiario) {
        this.idDeposito = idDeposito;
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

    public Double getCostoEstadiaDiario() { return costoEstadiaDiario; }
    public void setCostoEstadiaDiario(Double costoEstadiaDiario) { this.costoEstadiaDiario = costoEstadiaDiario; }
}
