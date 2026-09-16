// DTO para crear una nueva solicitud (sin IDs ni relaciones complejas)
package com.backend.ms_logistica.dto;

import com.backend.ms_logistica.model.UnidadPeso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SolicitudCreateDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer idCliente;

    private String descripcion;

    // Origen
    private String origenDireccion;
    @NotNull private Double origenLat;
    @NotNull private Double origenLon;

    // Destino
    private String destinoDireccion;
    @NotNull private Double destinoLat;
    @NotNull private Double destinoLon;

    // Datos del contenedor
    @NotNull @Positive private Double peso;
    @NotNull private UnidadPeso unidadPeso;
    @NotNull @Positive private Double volumen;

    // Constructores
    public SolicitudCreateDTO() {}

    // Getters y Setters
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getOrigenDireccion() { return origenDireccion; }
    public void setOrigenDireccion(String origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public Double getOrigenLat() { return origenLat; }
    public void setOrigenLat(Double origenLat) { this.origenLat = origenLat; }

    public Double getOrigenLon() { return origenLon; }
    public void setOrigenLon(Double origenLon) { this.origenLon = origenLon; }

    public String getDestinoDireccion() { return destinoDireccion; }
    public void setDestinoDireccion(String destinoDireccion) {
        this.destinoDireccion = destinoDireccion;
    }

    public Double getDestinoLat() { return destinoLat; }
    public void setDestinoLat(Double destinoLat) { this.destinoLat = destinoLat; }

    public Double getDestinoLon() { return destinoLon; }
    public void setDestinoLon(Double destinoLon) { this.destinoLon = destinoLon; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public UnidadPeso getUnidadPeso() { return unidadPeso; }
    public void setUnidadPeso(UnidadPeso unidadPeso) { this.unidadPeso = unidadPeso; }

    public Double getVolumen() { return volumen; }
    public void setVolumen(Double volumen) { this.volumen = volumen; }
}